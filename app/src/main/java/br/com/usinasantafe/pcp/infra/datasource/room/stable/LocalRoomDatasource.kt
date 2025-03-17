package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel

interface LocalRoomDatasource {
    suspend fun addAll(list: List<LocalRoomModel>): Result<Boolean>
    suspend fun listAll(): Result<List<LocalRoomModel>>
    suspend fun getDescr(id: Int): Result<String>
    suspend fun deleteAll(): Result<Boolean>
}