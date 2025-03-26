package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData

interface AdjustConfig {
    suspend operator fun invoke(version: String): Result<Boolean>
}

class IAdjustConfig(
    private val configRepository: ConfigRepository,
    private val startProcessSendData: StartProcessSendData
): AdjustConfig {

    override suspend fun invoke(version: String): Result<Boolean> {
        try {
            var checkVersion = true
            val resultHasConfig = configRepository.hasConfig()
            if (resultHasConfig.isFailure) {
                val e = resultHasConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IAdjustConfig",
                    message = e.message,
                    cause = e
                )
            }
            val hasConfig = resultHasConfig.getOrNull()!!
            if (hasConfig) {
                val resultGetConfig = configRepository.getConfig()
                if (resultGetConfig.isFailure) {
                    val e = resultGetConfig.exceptionOrNull()!!
                    return resultFailure(
                        context = "IAdjustConfig",
                        message = e.message,
                        cause = e
                    )
                }
                val config = resultGetConfig.getOrNull()!!
                config.version?.let {
                    if(it != version) {
                        checkVersion = false
                        val resultClean = configRepository.cleanConfig()
                        if (resultClean.isFailure) {
                            val e = resultClean.exceptionOrNull()!!
                            return resultFailure(
                                context = "IAdjustConfig",
                                message = e.message,
                                cause = e
                            )
                        }
                    }
                }
                if(checkVersion) {
                    startProcessSendData()
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IAdjustConfig",
                message = "-",
                cause = e
            )
        }
    }

}