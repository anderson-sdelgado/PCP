package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.errors.UsecaseException

interface CheckPassword {
    suspend operator fun invoke(password: String): Result<Boolean>
}

class ICheckPassword(
    private val configRepository: ConfigRepository
): CheckPassword {

    override suspend fun invoke(password: String): Result<Boolean> {
        try {
            val checkHasConfig = configRepository.hasConfig()

            if(checkHasConfig.isFailure)
                return Result.failure(checkHasConfig.exceptionOrNull()!!)

            if (!checkHasConfig.getOrNull()!!)
                return Result.success(true)

            val passwordBD = configRepository.getPassword()
            if(passwordBD.isFailure)
                return Result.failure(passwordBD.exceptionOrNull()!!)

            if (passwordBD.getOrNull() == password)
                return Result.success(true)

            return Result.success(false)

        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CheckPasswordConfig",
                    cause = e
                )
            )
        }
    }

}