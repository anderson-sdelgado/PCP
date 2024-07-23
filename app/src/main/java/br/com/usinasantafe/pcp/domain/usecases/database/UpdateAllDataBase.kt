package br.com.usinasantafe.pcp.domain.usecases.database

import br.com.usinasantafe.pcp.utils.FlagUpdate
import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.TEXT_SUCESS_UPDATE
import br.com.usinasantafe.pcp.domain.usecases.initial.SetCheckUpdateBDConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateAllDataBase {
    suspend operator fun invoke(count: Int = 0, size: Int = 16): Flow<Result<ResultUpdateDatabase>>
}

@Suppress("NAME_SHADOWING")
class UpdateAllDataBaseImpl @Inject constructor(
    private val updateColab: UpdateColab,
    private val updateEquip: UpdateEquip,
    private val updateLocal: UpdateLocal,
    private val updateTerceiro: UpdateTerceiro,
    private val updateVisitante: UpdateVisitante,
    private val setCheckUpdateBDConfig: SetCheckUpdateBDConfig,
) : UpdateAllDataBase {

    override suspend fun invoke(count: Int, size: Int): Flow<Result<ResultUpdateDatabase>> = flow {
        val size = size
        var count = count
        updateColab(count, size).collect { result ->
            result.fold(
                onSuccess = {
                    emit(Result.success(it))
                    count = it.count;
                },
                onFailure = { exception -> emit(Result.failure(exception)) }

            )
        }
        updateEquip(count, size).collect { result ->
            result.fold(
                onSuccess = {
                    emit(Result.success(it))
                    count = it.count;
                },
                onFailure = { exception -> emit(Result.failure(exception)) }
            )
        }
        updateLocal(count, size).collect { result ->
            result.fold(
                onSuccess = {
                    emit(Result.success(it))
                    count = it.count;
                },
                onFailure = { exception -> emit(Result.failure(exception)) }
            )
        }
        updateTerceiro(count, size).collect { result ->
            result.fold(
                onSuccess = {
                    emit(Result.success(it))
                    count = it.count;
                },
                onFailure = { exception -> emit(Result.failure(exception)) }
            )
        }
        updateVisitante(count, size).collect { result ->
            result.fold(
                onSuccess = {
                    emit(Result.success(it))
                    count = it.count;
                },
                onFailure = { exception -> emit(Result.failure(exception)) }
            )
        }
        setCheckUpdateBDConfig(FlagUpdate.UPDATED)
        emit(Result.success(ResultUpdateDatabase(size, TEXT_SUCESS_UPDATE, size)))
    }

}