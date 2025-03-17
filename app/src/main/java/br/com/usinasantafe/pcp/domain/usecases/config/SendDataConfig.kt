package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.errors.UsecaseException

interface SendDataConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        version: String
    ): Result<Int>
}

class ISendDataConfig (
    private val configRepository: ConfigRepository
) : SendDataConfig {

    override suspend fun invoke(
        number: String,
        password: String,
        version: String
    ): Result<Int> {
        try {
            val config = Config(
                number = number.toLong(),
                password = password,
                version = version,
            )
            return configRepository.send(config)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "SendDataConfig",
                    cause = e
                )
            )
        }
    }

}


