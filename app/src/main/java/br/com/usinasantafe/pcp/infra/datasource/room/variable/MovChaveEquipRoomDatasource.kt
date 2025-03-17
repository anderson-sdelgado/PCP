package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel

interface MovChaveEquipRoomDatasource {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun get(id: Int): Result<MovChaveEquipRoomModel>
    suspend fun listInside(): Result<List<MovChaveEquipRoomModel>>
    suspend fun listOpen(): Result<List<MovChaveEquipRoomModel>>
    suspend fun listSend(): Result<List<MovChaveEquipRoomModel>>
    suspend fun save(movChaveEquipRoomModel: MovChaveEquipRoomModel): Result<Long>
    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setIdEquip(
        idEquip: Int,
        id: Int
    ): Result<Boolean>
    suspend fun setObserv(
        observ: String?,
        id: Int
    ): Result<Boolean>
    suspend fun setMatricColab(
        matric: Int,
        id: Int
    ): Result<Boolean>
    suspend fun setSent(id: Int): Result<Boolean>
    suspend fun setOutside(id: Int): Result<Boolean>
}