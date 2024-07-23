package br.com.usinasantafe.pcp.domain.usecases.database

import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.TEXT_SUCESS_UPDATE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateVisitTerc {
    suspend operator fun invoke(count: Int = 0, size: Int = 7): Flow<Result<ResultUpdateDatabase>>
}

class UpdateVisitTercImpl @Inject constructor(
    private val updateTerceiro: UpdateTerceiro,
    private val updateVisitante: UpdateVisitante,
) : UpdateVisitTerc {

    override suspend fun invoke(count: Int, size: Int): Flow<Result<ResultUpdateDatabase>> = flow {
        var countUpdate = count
        updateTerceiro(countUpdate, size).collect { result ->
            result.fold(
                onSuccess = {
                    emit(Result.success(it))
                    countUpdate = it.count;
                },
                onFailure = { exception -> emit(Result.failure(exception)) }
            )
        }
        updateVisitante(countUpdate, size).collect { result ->
            result.fold(
                onSuccess = {
                    emit(Result.success(it))
                    countUpdate = it.count;
                },
                onFailure = { exception -> emit(Result.failure(exception)) }
            )
        }
        emit(Result.success(ResultUpdateDatabase(size, TEXT_SUCESS_UPDATE, size)))
    }

}