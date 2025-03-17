package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.StatusSend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetStatusSend {
    suspend operator fun invoke(): Flow<Result<StatusSend>>
}

class IGetStatusSend(
    private val configRepository: ConfigRepository,
): GetStatusSend {

    override suspend fun invoke(): Flow<Result<StatusSend>> = flow {
        val resultConfig = configRepository.getConfig()
        if(resultConfig.isFailure) {
            emit(
                Result.failure(
                    resultConfig.exceptionOrNull()!!
                )
            )
            return@flow
        }
        val config = resultConfig.getOrNull()!!
        emit(Result.success(config.statusSend))
    }

}