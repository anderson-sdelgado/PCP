package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovChaveEquipRoomDatasource {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun get(id: Int): Result<MovChaveEquipRoomModel>
    suspend fun listInside(): Result<List<MovChaveEquipRoomModel>>
    suspend fun listOpen(): Result<List<MovChaveEquipRoomModel>>
    suspend fun listSend(): Result<List<MovChaveEquipRoomModel>>
    suspend fun save(movChaveEquipRoomModel: MovChaveEquipRoomModel): Result<Long>
    suspend fun setClose(id: Int): EmptyResult
    suspend fun setIdEquip(
        idEquip: Int,
        id: Int
    ): EmptyResult
    suspend fun setObserv(
        observ: String?,
        id: Int
    ): EmptyResult
    suspend fun setMatricColab(
        matric: Int,
        id: Int
    ): EmptyResult
    suspend fun setSent(id: Int): EmptyResult
    suspend fun setOutside(id: Int): EmptyResult
}