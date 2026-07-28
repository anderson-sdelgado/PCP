package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipProprioRoomDatasource {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun delete(movEquipProprioRoomModel: MovEquipProprioRoomModel): EmptyResult
    suspend fun get(id: Int): Result<MovEquipProprioRoomModel>
    suspend fun listOpen(): Result<List<MovEquipProprioRoomModel>>
    suspend fun listSend(): Result<List<MovEquipProprioRoomModel>>
    suspend fun listSent(): Result<List<MovEquipProprioRoomModel>>
    suspend fun save(movEquipProprioRoomModel: MovEquipProprioRoomModel): Result<Long>
    suspend fun setClose(id: Int): EmptyResult
    suspend fun setDestino(destino: String, id: Int): EmptyResult
    suspend fun setIdEquip(idEquip: Int, id: Int): EmptyResult
    suspend fun setMatricColab(matricColab: Int, id: Int): EmptyResult
    suspend fun setNotaFiscal(notaFiscal: Int?, id: Int): EmptyResult
    suspend fun setObserv(observ: String?, id: Int): EmptyResult
    suspend fun setSent(id: Int): EmptyResult
    suspend fun setSend(id: Int): EmptyResult
}