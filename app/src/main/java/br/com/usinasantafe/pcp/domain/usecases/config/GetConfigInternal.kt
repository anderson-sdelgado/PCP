package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.errors.UsecaseException
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
            val checkHasConfig = configRepository.hasConfig()
            if(checkHasConfig.isFailure)
                return Result.failure(checkHasConfig.exceptionOrNull()!!)

            if (!checkHasConfig.getOrNull()!!)
                return Result.success(null)

            val config = configRepository.getConfig()
            if(config.isFailure)
                return Result.failure(config.exceptionOrNull()!!)

            return Result.success(config.getOrNull()!!.toConfigModel())

        } catch (e: Exception) {

            return Result.failure(
                UsecaseException(
                    function = "RecoverConfigInternal",
                    cause = e
                )
            )

        }

    }

}
