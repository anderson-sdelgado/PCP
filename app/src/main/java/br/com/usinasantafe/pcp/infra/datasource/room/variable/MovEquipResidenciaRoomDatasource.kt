package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipResidenciaRoomDatasource {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun delete(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): EmptyResult
    suspend fun get(id: Int): Result<MovEquipResidenciaRoomModel>
    suspend fun listOpen(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun listInside(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun listSend(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun listSent(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun save(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Long>
    suspend fun setClose(id: Int): EmptyResult
    suspend fun setMotorista(motorista: String, id: Int): EmptyResult
    suspend fun setObserv(observ: String?, id: Int): EmptyResult
    suspend fun setOutside(id: Int): EmptyResult
    suspend fun setPlaca(placa: String, id: Int): EmptyResult
    suspend fun setVeiculo(veiculo: String, id: Int): EmptyResult
    suspend fun setSent(id: Int): EmptyResult
}