package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel

interface MovEquipProprioRoomDatasource {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun delete(movEquipProprioRoomModel: MovEquipProprioRoomModel): Result<Boolean>
    suspend fun get(id: Int): Result<MovEquipProprioRoomModel>
    suspend fun listOpen(): Result<List<MovEquipProprioRoomModel>>
    suspend fun listSend(): Result<List<MovEquipProprioRoomModel>>
    suspend fun listSent(): Result<List<MovEquipProprioRoomModel>>
    suspend fun save(movEquipProprioRoomModel: MovEquipProprioRoomModel): Result<Long>
    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setDestino(
        destino: String,
        id: Int
    ): Result<Boolean>

    suspend fun setIdEquip(
        idEquip: Int,
        id: Int
    ): Result<Boolean>

    suspend fun setMatricColab(
        matricColab: Int,
        id: Int
    ): Result<Boolean>

    suspend fun setNotaFiscal(
        notaFiscal: Int?,
        id: Int
    ): Result<Boolean>

    suspend fun setObserv(
        observ: String?,
        id: Int
    ): Result<Boolean>

    suspend fun setSent(id: Int): Result<Boolean>
    suspend fun setSend(id: Int): Result<Boolean>
}