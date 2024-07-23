package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.toColab
import br.com.usinasantafe.pcp.infra.models.room.stable.toColabModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ColabRepositoryImpl @Inject constructor(
    private val colabRoomDatasource: ColabRoomDatasource,
    private val colabRetrofitDatasource: ColabRetrofitDatasource,
): ColabRepository {

    override suspend fun addAll(list: List<Colab>) {
        colabRoomDatasource.addAllColab(*list.map { it.toColabModel() }.toTypedArray())
    }

    override suspend fun deleteAll() {
        colabRoomDatasource.deleteAllColab()
    }

    override suspend fun recoverAll(token: String): Flow<Result<List<Colab>>> = flow {
        colabRetrofitDatasource.recoverAll(token)
            .catch { exception -> emit(Result.failure(exception)) }
            .collect { result ->
                result.fold(
                    onSuccess = { colabModelList ->
                        emit(Result.success(colabModelList.map { it.toColab() }))
                    },
                    onFailure = { exception -> emit(Result.failure(exception)) }
                )
            }
    }

    override suspend fun checkColabMatric(matric: Long): Boolean {
        return colabRoomDatasource.checkColabMatric(matric)
    }

    override suspend fun getColabMatric(matric: Long): Colab {
        return colabRoomDatasource.getColabMatric(matric).toColab()
    }

}