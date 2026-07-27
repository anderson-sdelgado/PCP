package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface SetMatricVigiaConfig {
    suspend operator fun invoke(matric: String): Result<Boolean>
}

class ISetMatricVigiaConfig @Inject constructor(
    private val configRepository: ConfigRepository,
): SetMatricVigiaConfig {

    override suspend fun invoke(matric: String): Result<Boolean> {
        try {
            val result = configRepository.setMatricVigia(matric.toInt())
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetMatricVigiaConfig",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ISetMatricVigiaConfig",
                message = "-",
                cause = e
            )
        }
    }

}