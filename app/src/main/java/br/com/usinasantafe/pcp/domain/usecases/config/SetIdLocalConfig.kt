package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository

interface SetIdLocalConfig {
    suspend operator fun invoke(idLocal: Int): Result<Boolean>
}

class ISetIdLocalConfig(
    private val configRepository: ConfigRepository,
): SetIdLocalConfig {

    override suspend fun invoke(idLocal: Int): Result<Boolean> {
        try {
            val result = configRepository.setIdLocal(idLocal)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdLocalConfig",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ISetIdLocalConfig",
                message = "-",
                cause = e
            )
        }
    }

}