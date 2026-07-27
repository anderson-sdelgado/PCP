package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.ChaveRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface ChaveRoomDatasource {
    suspend fun addAll(list: List<ChaveRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun get(id: Int): Result<ChaveRoomModel>
    suspend fun listAll(): Result<List<ChaveRoomModel>>
}