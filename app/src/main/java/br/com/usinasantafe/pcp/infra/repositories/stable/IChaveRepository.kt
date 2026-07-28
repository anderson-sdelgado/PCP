package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Chave
import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.ChaveRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ChaveRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class IChaveRepository @Inject constructor(
    private val chaveRoomDatasource: ChaveRoomDatasource,
    private val chaveRetrofitDatasource: ChaveRetrofitDatasource
): ChaveRepository {

    override suspend fun addAll(list: List<Chave>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            chaveRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            chaveRoomDatasource.deleteAll()
        }

    override suspend fun get(id: Int): Result<Chave> =
        call(getClassAndMethod()) {
            val model = chaveRoomDatasource.get(id).getOrThrow()
            model.roomModelToEntity()
        }

    override suspend fun listAll(): Result<List<Chave>> =
        call(getClassAndMethod()) {
            val modelList = chaveRoomDatasource.listAll().getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }

    override suspend fun listAll(token: String): Result<List<Chave>> =
        call(getClassAndMethod()) {
            val modelList = chaveRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }


}