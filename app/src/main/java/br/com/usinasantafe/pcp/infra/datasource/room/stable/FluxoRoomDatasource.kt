package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.FluxoRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface FluxoRoomDatasource {
    suspend fun addAll(list: List<FluxoRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun getById(id: Int): Result<FluxoRoomModel>
}