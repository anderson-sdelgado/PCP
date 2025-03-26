package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.errors.resultFailure

interface SaveDataConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        version: String,
        idBD: Int,
    ): Result<Boolean>
}

class ISaveDataConfig(
    private val configRepository: ConfigRepository
): SaveDataConfig {

    override suspend fun invoke(
        number: String,
        password: String,
        version: String,
        idBD: Int
    ): Result<Boolean> {
        try {
            val result =configRepository.saveInitial(
                number = number.toLong(),
                password= password,
                version = version,
                idBD = idBD
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveDataConfig",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ISaveDataConfig",
                message = "-",
                cause = e
            )
        }
    }

}