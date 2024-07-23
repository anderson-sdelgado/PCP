package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface SetStatusSendConfig {
    suspend operator fun invoke(statusSend: StatusSend)
}

class SetStatusSendConfigImpl @Inject constructor(
    private val configRepository: ConfigRepository,
): SetStatusSendConfig {

    override suspend fun invoke(statusSend: StatusSend) {
        configRepository.setStatusSendConfig(statusSend)
    }

}