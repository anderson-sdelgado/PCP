package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository

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
            val result = configRepository.send(config)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendDataConfig",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ISendDataConfig",
                message = "-",
                cause = e
            )
        }
    }

}


