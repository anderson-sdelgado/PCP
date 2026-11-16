package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.callFlow
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetStatusSend {
    suspend operator fun invoke(): Flow<StatusSend>
}

class IGetStatusSend @Inject constructor(
    private val configRepository: ConfigRepository,
): GetStatusSend {

    override suspend fun invoke(): Flow<StatusSend> =
        callFlow(getClassAndMethod()) {
            configRepository.getStatusSend()
        }
}