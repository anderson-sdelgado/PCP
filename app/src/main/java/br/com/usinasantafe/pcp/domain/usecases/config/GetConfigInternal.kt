package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.presenter.model.toConfigModel
import br.com.usinasantafe.pcp.presenter.model.ConfigModel
import javax.inject.Inject

interface GetConfigInternal {
    suspend operator fun invoke(): Result<ConfigModel?>
}

class IGetConfigInternal @Inject constructor(
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
                    cause = e.cause
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
                    cause = e.cause
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
