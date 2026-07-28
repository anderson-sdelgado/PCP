package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipVisitTercRoomDatasource {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun delete(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): EmptyResult
    suspend fun get(id: Int): Result<MovEquipVisitTercRoomModel>
    suspend fun listOpen(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun listInside(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun listSend(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun listSent(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun save(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Long>
    suspend fun setClose(id: Int): EmptyResult
    suspend fun setDestino(destino: String, id: Int): EmptyResult
    suspend fun setIdVisitTerc(idVisitTerc: Int, id: Int): EmptyResult
    suspend fun setObserv(observ: String?, id: Int): EmptyResult
    suspend fun setOutside(id: Int): EmptyResult
    suspend fun setPlaca(placa: String, id: Int): EmptyResult
    suspend fun setVeiculo(veiculo: String, id: Int): EmptyResult
    suspend fun setSent(id: Int): EmptyResult
    suspend fun setSend(id: Int): EmptyResult
}