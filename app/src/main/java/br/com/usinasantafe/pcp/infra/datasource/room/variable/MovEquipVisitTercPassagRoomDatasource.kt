package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipVisitTercPassagRoomDatasource {
    suspend fun add(idVisitTerc: Int, id: Int): EmptyResult
    suspend fun addAll(list: List<MovEquipVisitTercPassagRoomModel>): EmptyResult
    suspend fun delete(id: Int): EmptyResult
    suspend fun delete(idVisitTerc: Int, id: Int): EmptyResult
    suspend fun list(id: Int): Result<List<MovEquipVisitTercPassagRoomModel>>
}