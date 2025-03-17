package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.StatusSend

interface SetStatusSend {
    suspend operator fun invoke(statusSend: StatusSend): Result<Boolean>
}

class ISetStatusSend(
    private val configRepository: ConfigRepository
): SetStatusSend {

    override suspend fun invoke(statusSend: StatusSend): Result<Boolean> {
        return configRepository.setStatusSend(statusSend)
    }

}