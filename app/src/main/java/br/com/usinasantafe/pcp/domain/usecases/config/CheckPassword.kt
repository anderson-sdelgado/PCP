package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository

interface CheckPassword {
    suspend operator fun invoke(password: String): Result<Boolean>
}

class ICheckPassword(
    private val configRepository: ConfigRepository
): CheckPassword {

    override suspend fun invoke(password: String): Result<Boolean> {
        try {
            val resultCheckHasConfig = configRepository.hasConfig()
            if (resultCheckHasConfig.isFailure) {
                val e = resultCheckHasConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckPassword",
                    message = e.message,
                    cause = e
                )
            }
            if (!resultCheckHasConfig.getOrNull()!!)
                return Result.success(true)
            val resultPasswordBD = configRepository.getPassword()
            if (resultPasswordBD.isFailure) {
                val e = resultPasswordBD.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckPassword",
                    message = e.message,
                    cause = e
                )
            }
            if (resultPasswordBD.getOrNull() == password)
                return Result.success(true)
            return Result.success(false)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckPassword",
                message = "-",
                cause = e
            )
        }
    }

}