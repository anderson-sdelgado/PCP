package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalTrabRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.LocalTrabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalTrabRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class ILocalTrabRepository @Inject constructor(
    private val localTrabRoomDatasource: LocalTrabRoomDatasource,
    private val localTrabRetrofitDatasource: LocalTrabRetrofitDatasource
): LocalTrabRepository {

    override suspend fun addAll(list: List<LocalTrab>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            localTrabRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            localTrabRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun getDescrById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            localTrabRoomDatasource.getDescrById(id).getOrThrow()
        }

    override suspend fun listAll(token: String): Result<List<LocalTrab>> =
        call(getClassAndMethod()) {
            val modelList = localTrabRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

}