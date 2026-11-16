package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.lib.FlagUpdate
import javax.inject.Inject

interface SetFinishUpdateAllTable {
    suspend operator fun invoke(): Result<Boolean>
}

class ISetFinishUpdateAllTable @Inject constructor(
    private val configRepository: ConfigRepository,
): SetFinishUpdateAllTable {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val result = configRepository.setFlagUpdate(FlagUpdate.UPDATED)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetCheckUpdateAllTable",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ISetCheckUpdateAllTable",
                message = "-",
                cause = e
            )
        }
    }

}