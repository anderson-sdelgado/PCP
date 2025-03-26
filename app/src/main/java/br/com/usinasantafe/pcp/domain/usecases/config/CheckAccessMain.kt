package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.FlagUpdate

interface CheckAccessMain {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckAccessMain(
    private val configRepository: ConfigRepository
): CheckAccessMain {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultCheckHasConfig = configRepository.hasConfig()
            if (resultCheckHasConfig.isFailure) {
                val e = resultCheckHasConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckAccessMain",
                    message = e.message,
                    cause = e
                )
            }
            if (!resultCheckHasConfig.getOrNull()!!)
                return Result.success(false)
            val resultFlagUpdate = configRepository.getFlagUpdate()
            if (resultFlagUpdate.isFailure) {
                val e = resultFlagUpdate.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckAccessMain",
                    message = e.message,
                    cause = e
                )
            }
            if (resultFlagUpdate.getOrNull()!! == FlagUpdate.OUTDATED)
                return Result.success(false)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckAccessMain",
                message = "-",
                cause = e
            )
        }
    }

}