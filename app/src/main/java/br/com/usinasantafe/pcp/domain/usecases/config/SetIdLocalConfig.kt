package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository

interface SetIdLocalConfig {
    suspend operator fun invoke(idLocal: Int): Result<Boolean>
}

class ISetIdLocalConfig(
    private val configRepository: ConfigRepository,
): SetIdLocalConfig {

    override suspend fun invoke(idLocal: Int): Result<Boolean> {
        try {
            return configRepository.setIdLocal(idLocal)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "SetIdLocalConfig",
                    cause = e
                )
            )
        }
    }

}