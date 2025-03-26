package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.StatusSend

interface SetStatusSend {
    suspend operator fun invoke(statusSend: StatusSend): Result<Boolean>
}

class ISetStatusSend(
    private val configRepository: ConfigRepository
): SetStatusSend {

    override suspend fun invoke(statusSend: StatusSend): Result<Boolean> {
        val result = configRepository.setStatusSend(statusSend)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetStatusSend",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}