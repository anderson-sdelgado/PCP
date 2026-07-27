package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.RLocalFluxoRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface RLocalFluxoRoomDatasource {
    suspend fun addAll(list: List<RLocalFluxoRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun list(idLocal: Int): Result<List<RLocalFluxoRoomModel>>
}