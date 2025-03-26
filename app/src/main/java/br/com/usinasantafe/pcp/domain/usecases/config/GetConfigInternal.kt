package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.presenter.configuration.config.ConfigModel
import br.com.usinasantafe.pcp.presenter.configuration.config.toConfigModel

interface GetConfigInternal {
    suspend operator fun invoke(): Result<ConfigModel?>
}

class IGetConfigInternal(
    private val configRepository: ConfigRepository
): GetConfigInternal {

    override suspend fun invoke(): Result<ConfigModel?> {
        try{
            val resulCheckHasConfig = configRepository.hasConfig()
            if (resulCheckHasConfig.isFailure) {
                val e = resulCheckHasConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetConfigInternal",
                    message = e.message,
                    cause = e
                )
            }
            if (!resulCheckHasConfig.getOrNull()!!)
                return Result.success(null)
            val resulGetConfig = configRepository.getConfig()
            if (resulGetConfig.isFailure) {
                val e = resulGetConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetConfigInternal",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resulGetConfig.getOrNull()!!.toConfigModel())
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetConfigInternal",
                message = "-",
                cause = e
            )
        }

    }

}
