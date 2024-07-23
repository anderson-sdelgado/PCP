package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel


interface MovEquipProprioDatasourceRoom {

    suspend fun checkMovSend(): Boolean

    suspend fun deleteMov(movEquipProprioRoomModel: MovEquipProprioRoomModel): Boolean

    suspend fun lastIdMovStatusOpen(): Long

    suspend fun listMovEquipProprioOpen(): List<MovEquipProprioRoomModel>

    suspend fun listMovEquipProprioSend(): List<MovEquipProprioRoomModel>

    suspend fun listMovEquipProprioSent(): List<MovEquipProprioRoomModel>

    suspend fun saveMovEquipProprioOpen(movEquipProprioRoomModel: MovEquipProprioRoomModel): Boolean

    suspend fun updateStatusSendMovEquipProprio(
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean

    suspend fun updateDestinoMovEquipProprio(
        destino: String,
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean

    suspend fun updateIdEquipMovEquipProprio(
        idEquip: Long,
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean

    suspend fun updateNroColabMovEquipProprio(
        nroMatric: Long,
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean

    suspend fun updateNotaFiscalMovEquipProprio(
        notaFiscal: Long,
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean

    suspend fun updateObservMovEquipProprio(
        observ: String?,
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean

    suspend fun updateStatusMovEquipProprioClose(movEquipProprioRoomModel: MovEquipProprioRoomModel): Boolean

    suspend fun updateStatusMovEquipProprioSent(idMov: Long): Boolean

}