package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.LocalTrabRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface LocalTrabRoomDatasource {
    suspend fun addAll(list: List<LocalTrabRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun getDescrById(id: Int): Result<String>
}