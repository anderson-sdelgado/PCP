package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipProprioPassagRoomDatasource {
    suspend fun add(matricColab: Int, id: Int): EmptyResult
    suspend fun addAll(list: List<MovEquipProprioPassagRoomModel>): EmptyResult
    suspend fun delete(id: Int): EmptyResult
    suspend fun delete(matricColab: Int, id: Int): EmptyResult
    suspend fun list(id: Int): Result<List<MovEquipProprioPassagRoomModel>>
}