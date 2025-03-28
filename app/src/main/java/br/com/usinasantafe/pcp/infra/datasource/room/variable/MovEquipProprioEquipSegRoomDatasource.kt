package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel

interface MovEquipProprioEquipSegRoomDatasource {
    suspend fun add(
        idEquip: Int,
        id: Int
    ): Result<Boolean>

    suspend fun addAll(list: List<MovEquipProprioEquipSegRoomModel>): Result<Boolean>
    suspend fun delete(id: Int): Result<Boolean>
    suspend fun delete(
        idEquip: Int,
        id: Int
    ): Result<Boolean>

    suspend fun list(id: Int): Result<List<MovEquipProprioEquipSegRoomModel>>
}