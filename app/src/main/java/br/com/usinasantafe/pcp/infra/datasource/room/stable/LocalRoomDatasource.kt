package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface LocalRoomDatasource {
    suspend fun addAll(list: List<LocalRoomModel>): EmptyResult
    suspend fun listAll(): Result<List<LocalRoomModel>>
    suspend fun getDescrById(id: Int): Result<String>
    suspend fun deleteAll(): EmptyResult
}