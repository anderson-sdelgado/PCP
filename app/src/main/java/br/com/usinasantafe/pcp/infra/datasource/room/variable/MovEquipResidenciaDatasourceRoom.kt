package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel

interface MovEquipResidenciaDatasourceRoom {

    suspend fun checkMovSend(): Boolean

    suspend fun deleteMovEquipResidencia(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Boolean

    suspend fun insertMovEquipResidenciaOpen(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Boolean

    suspend fun insertMovEquipResidenciaOutside(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Boolean

    suspend fun lastIdMovStatusOpen(): Long

    suspend fun listMovEquipResidenciaInside(): List<MovEquipResidenciaRoomModel>

    suspend fun listMovEquipResidenciaOpen(): List<MovEquipResidenciaRoomModel>

    suspend fun listMovEquipResidenciaSend(): List<MovEquipResidenciaRoomModel>

    suspend fun listMovEquipResidenciaSent(): List<MovEquipResidenciaRoomModel>

    suspend fun updateVeiculoMovEquipResidencia(
        veiculo: String,
        movEquipVisitTercRoomModel: MovEquipResidenciaRoomModel
    ): Boolean

    suspend fun updatePlacaMovEquipResidencia(
        placa: String,
        movEquipVisitTercRoomModel: MovEquipResidenciaRoomModel
    ): Boolean

    suspend fun updateMotoristaMovEquipResidencia(
        motorista: String,
        movEquipVisitTercRoomModel: MovEquipResidenciaRoomModel
    ): Boolean

    suspend fun updateObservMovEquipResidencia(
        observ: String?,
        movEquipVisitTercRoomModel: MovEquipResidenciaRoomModel
    ): Boolean

    suspend fun updateStatusSentMovEquipResidencia(idMov: Long): Boolean

    suspend fun updateStatusMovEquipResidenciaOutside(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Boolean

    suspend fun updateStatusMovEquipResidenciaClose(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Boolean

}