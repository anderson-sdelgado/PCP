package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.lib.StatusSend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetStatusSend {
    suspend operator fun invoke(): Flow<Result<StatusSend>>
}

class IGetStatusSend @Inject constructor(
    private val configRepository: ConfigRepository,
): GetStatusSend {

    override suspend fun invoke(): Flow<Result<StatusSend>> = flow {
        val result = configRepository.getConfig()
        if(result.isFailure) {
            val e = result.exceptionOrNull()!!
            emit(
                resultFailure(
                    context = "IGetStatusSend",
                    message = e.message,
                    cause = e.cause
                )
            )
            return@flow
        }
        val config = result.getOrNull()!!
        emit(Result.success(config.statusSend))
    }

}