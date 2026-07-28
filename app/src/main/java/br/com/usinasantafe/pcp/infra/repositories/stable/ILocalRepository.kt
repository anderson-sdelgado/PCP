package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class ILocalRepository @Inject constructor(
    private val localRoomDatasource: LocalRoomDatasource,
    private val localRetrofitDatasource: LocalRetrofitDatasource
): LocalRepository {
    
    override suspend fun addAll(list: List<Local>): EmptyResult =
        call(getClassAndMethod()) {
            val localModelList = list.map { it.entityToRoomModel() }
            localRoomDatasource.addAll(localModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            localRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(): Result<List<Local>> =
        call(getClassAndMethod()) {
            val modelList = localRoomDatasource.listAll().getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }

    override suspend fun getDescrById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            localRoomDatasource.getDescrById(id).getOrThrow()
        }

    override suspend fun listAll(token: String): Result<List<Local>> =
        call(getClassAndMethod()) {
            val modelList = localRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

}