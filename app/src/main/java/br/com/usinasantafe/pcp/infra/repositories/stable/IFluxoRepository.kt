package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.repositories.stable.FluxoRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.FluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.FluxoRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class IFluxoRepository @Inject constructor(
    private val fluxoRoomDatasource: FluxoRoomDatasource,
    private val fluxoRetrofitDatasource: FluxoRetrofitDatasource
): FluxoRepository {

    override suspend fun addAll(list: List<Fluxo>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            fluxoRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            fluxoRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun getById(id: Int): Result<Fluxo> =
        call(getClassAndMethod()) {
            val model = fluxoRoomDatasource.getById(id).getOrThrow()
            model.roomModelToEntity()
        }

    override suspend fun listAll(token: String): Result<List<Fluxo>> =
        call(getClassAndMethod()) {
            val modelList = fluxoRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

}