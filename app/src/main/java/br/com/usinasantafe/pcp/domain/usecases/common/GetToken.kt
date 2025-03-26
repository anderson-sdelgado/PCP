package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.token

interface GetToken {
    suspend operator fun invoke(): Result<String>
}

class IGetToken(
    private val configRepository: ConfigRepository
): GetToken {

    override suspend fun invoke(): Result<String> {
        try {
            val resultGetConfig = configRepository.getConfig()
            if (resultGetConfig.isFailure) {
                val e = resultGetConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetToken",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultGetConfig.getOrNull()!!
            val token = token(
                number = config.number!!,
                version = config.version!!,
                idBD = config.idBD!!
            )
            return Result.success(token)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetToken",
                message = "-",
                cause = e
            )
        }
    }

}