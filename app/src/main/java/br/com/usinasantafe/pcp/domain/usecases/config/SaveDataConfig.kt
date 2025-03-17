package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.errors.UsecaseException

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
        return try {
            configRepository.saveInitial(
                number = number.toLong(),
                password= password,
                version = version,
                idBD = idBD
            )
        } catch (e: Exception){
            Result.failure(
                UsecaseException(
                    function = "SaveDataConfig",
                    cause = e
                )
            )
        }
    }

}