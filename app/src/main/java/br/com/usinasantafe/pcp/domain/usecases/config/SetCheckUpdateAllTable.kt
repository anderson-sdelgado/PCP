package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.FlagUpdate

interface SetCheckUpdateAllTable {
    suspend operator fun invoke(flagUpdate: FlagUpdate): Result<Boolean>
}


class ISetCheckUpdateAllTable (
    private val configRepository: ConfigRepository,
): SetCheckUpdateAllTable {

    override suspend fun invoke(flagUpdate: FlagUpdate): Result<Boolean> {
        return try {
            configRepository.setFlagUpdate(flagUpdate)
        } catch (e: Exception){
            Result.failure(
                UsecaseException(
                    function = "SetCheckUpdateAllTable",
                    cause = e
                )
            )
        }
    }

}