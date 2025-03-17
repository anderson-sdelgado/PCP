package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.LocalTrabRoomModel

interface LocalTrabRoomDatasource {
    suspend fun addAll(list: List<LocalTrabRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getDescr(id: Int): Result<String>
}