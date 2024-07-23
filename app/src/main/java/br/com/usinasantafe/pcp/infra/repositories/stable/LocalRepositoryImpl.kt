package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.toLocal
import br.com.usinasantafe.pcp.infra.models.room.stable.toLocalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val localRoomDatasource: LocalRoomDatasource,
    private val localRetrofitDatasource: LocalRetrofitDatasource,
): LocalRepository {

    override suspend fun addAllLocal(list: List<br.com.usinasantafe.pcp.domain.entities.stable.Local>) {
        localRoomDatasource.addAllLocal(*list.map { it.toLocalModel() }.toTypedArray())
    }

    override suspend fun deleteAllLocal() {
        localRoomDatasource.deleteAllLocal()
    }

    override suspend fun listAllLocal(): List<br.com.usinasantafe.pcp.domain.entities.stable.Local> {
        return localRoomDatasource.listAllLocal().map { it.toLocal() }
    }

    override suspend fun recoverAllLocal(token: String): Flow<Result<List<br.com.usinasantafe.pcp.domain.entities.stable.Local>>> = flow {
        localRetrofitDatasource.getAllLocal(token)
            .catch { exception -> emit(Result.failure(exception)) }
            .collect { result ->
                result.fold(
                    onSuccess = { localModelList ->
                        emit(Result.success(localModelList.map { it.toLocal() }))
                    },
                    onFailure = { exception -> emit(Result.failure(exception)) }
                )
            }
    }

    override suspend fun getLocalId(id: Long): br.com.usinasantafe.pcp.domain.entities.stable.Local {
        return localRoomDatasource.getLocalId(id).toLocal()
    }

}