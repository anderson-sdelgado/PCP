package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel

interface MovEquipVisitTercPassagRoomDatasource {
    suspend fun add(
        idVisitTerc: Int,
        id: Int
    ): Result<Boolean>

    suspend fun addAll(list: List<MovEquipVisitTercPassagRoomModel>): Result<Boolean>
    suspend fun delete(id: Int): Result<Boolean>
    suspend fun delete(
        idVisitTerc: Int,
        id: Int
    ): Result<Boolean>

    suspend fun list(id: Int): Result<List<MovEquipVisitTercPassagRoomModel>>
}