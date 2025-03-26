package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.FlagUpdate

interface SetCheckUpdateAllTable {
    suspend operator fun invoke(flagUpdate: FlagUpdate): Result<Boolean>
}

class ISetCheckUpdateAllTable (
    private val configRepository: ConfigRepository,
): SetCheckUpdateAllTable {

    override suspend fun invoke(flagUpdate: FlagUpdate): Result<Boolean> {
        try {
            val result = configRepository.setFlagUpdate(flagUpdate)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetCheckUpdateAllTable",
                    message = e.message,
                    cause = e
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