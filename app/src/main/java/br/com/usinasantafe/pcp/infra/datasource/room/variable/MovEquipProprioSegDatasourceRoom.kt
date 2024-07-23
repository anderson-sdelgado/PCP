package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioSegRoomModel

interface MovEquipProprioSegDatasourceRoom {

    suspend fun addAllMovEquipProprioSeg(vararg movEquipProprioSegRoomModels: MovEquipProprioSegRoomModel): Boolean

    suspend fun addMovEquipProprioSeg(movEquipProprioSegRoomModel: MovEquipProprioSegRoomModel): Boolean

    suspend fun listMovEquipProprioSegIdMov(idMov: Long): List<MovEquipProprioSegRoomModel>

    suspend fun deleteMovEquipProprioSeg(movEquipProprioSegRoomModel: MovEquipProprioSegRoomModel): Boolean

}