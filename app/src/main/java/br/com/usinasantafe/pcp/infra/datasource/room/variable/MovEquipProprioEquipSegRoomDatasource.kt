package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipProprioEquipSegRoomDatasource {
    suspend fun add(idEquip: Int, id: Int): EmptyResult
    suspend fun addAll(list: List<MovEquipProprioEquipSegRoomModel>): EmptyResult
    suspend fun delete(id: Int): EmptyResult
    suspend fun delete(idEquip: Int, id: Int): EmptyResult
    suspend fun list(id: Int): Result<List<MovEquipProprioEquipSegRoomModel>>
}