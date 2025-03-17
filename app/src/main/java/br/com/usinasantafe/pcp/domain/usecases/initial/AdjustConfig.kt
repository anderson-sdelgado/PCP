package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
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
            if (resultHasConfig.isFailure)
                return Result.failure(resultHasConfig.exceptionOrNull()!!)
            val hasConfig = resultHasConfig.getOrNull()!!
            if (hasConfig) {
                val resultConfig = configRepository.getConfig()
                if (resultConfig.isFailure)
                    return Result.failure(resultConfig.exceptionOrNull()!!)
                val config = resultConfig.getOrNull()!!
                config.version?.let {
                    if(it != version) {
                        checkVersion = false
                        val resultClean = configRepository.cleanConfig()
                        if (resultClean.isFailure)
                            return Result.failure(resultClean.exceptionOrNull()!!)
                    }
                }
                if(checkVersion) {
                    startProcessSendData()
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "StartAppImpl",
                    cause = e
                )
            )
        }
    }

}