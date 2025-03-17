package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel

interface MovEquipProprioPassagRoomDatasource {
    suspend fun add(
        matricColab: Int,
        id: Int
    ): Result<Boolean>

    suspend fun addAll(list: List<MovEquipProprioPassagRoomModel>): Result<Boolean>
    suspend fun delete(id: Int): Result<Boolean>
    suspend fun delete(
        matricColab: Int,
        id: Int
    ): Result<Boolean>

    suspend fun list(id: Int): Result<List<MovEquipProprioPassagRoomModel>>
}