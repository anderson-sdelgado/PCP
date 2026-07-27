package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface ColabRoomDatasource {
    suspend fun addAll(list: List<ColabRoomModel>): EmptyResult
    suspend fun checkMatric(matric: Int): Result<Boolean>
    suspend fun getNomeByMatric(matric: Int): Result<String>
    suspend fun deleteAll(): EmptyResult
}