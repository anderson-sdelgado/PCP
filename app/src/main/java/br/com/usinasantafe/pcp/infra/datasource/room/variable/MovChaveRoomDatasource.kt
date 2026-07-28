package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovChaveRoomDatasource {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun get(id: Int): Result<MovChaveRoomModel>
    suspend fun listInside(): Result<List<MovChaveRoomModel>>
    suspend fun listOpen(): Result<List<MovChaveRoomModel>>
    suspend fun listSend(): Result<List<MovChaveRoomModel>>
    suspend fun save(movChaveRoomModel: MovChaveRoomModel): Result<Long>
    suspend fun setClose(id: Int): EmptyResult
    suspend fun setIdChave(idChave: Int, id: Int): EmptyResult
    suspend fun setObserv(observ: String?, id: Int): EmptyResult
    suspend fun setMatricColab(matric: Int, id: Int): EmptyResult
    suspend fun setSent(id: Int): EmptyResult
    suspend fun setOutside(id: Int): EmptyResult
}