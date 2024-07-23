package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetStatusSendConfig {
    suspend operator fun invoke(): Flow<StatusSend>
}

class GetStatusSendConfigImpl @Inject constructor (
    private val configRepository: ConfigRepository,
): GetStatusSendConfig {

    override suspend fun invoke(): Flow<StatusSend> = flow {
        emit(configRepository.getConfig().statusEnvio)
    }

}