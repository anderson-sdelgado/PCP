package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.RLocalFluxoRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.RLocalFluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.RLocalFluxoRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class IRLocalFluxoRepository @Inject constructor(
    private val rLocalFluxoRoomDatasource: RLocalFluxoRoomDatasource,
    private val rLocalFluxoRetrofitDatasource: RLocalFluxoRetrofitDatasource
): RLocalFluxoRepository {

    override suspend fun addAll(list: List<RLocalFluxo>): EmptyResult =
        call(getClassAndMethod()) {
            val modelList = list.map { it.entityToRoomModel() }
            rLocalFluxoRoomDatasource.addAll(modelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            rLocalFluxoRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listByIdLocal(idLocal: Int): Result<List<RLocalFluxo>> =
        call(getClassAndMethod()) {
            val modelList = rLocalFluxoRoomDatasource.list(idLocal).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }

    override suspend fun listAll(token: String): Result<List<RLocalFluxo>> =
        call(getClassAndMethod()) {
            val modelList = rLocalFluxoRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

}