package br.com.usinasantafe.pcp.infra.datasource.room.variable

import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel

interface MovEquipVisitTercRoomDatasource {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun delete(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Boolean>
    suspend fun get(id: Int): Result<MovEquipVisitTercRoomModel>
    suspend fun listOpen(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun listInside(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun listSend(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun listSent(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun save(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Long>
    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setDestino(
        destino: String,
        id: Int
    ): Result<Boolean>

    suspend fun setIdVisitTerc(
        idVisitTerc: Int,
        id: Int
    ): Result<Boolean>

    suspend fun setObserv(
        observ: String?,
        id: Int
    ): Result<Boolean>

    suspend fun setOutside(id: Int): Result<Boolean>
    suspend fun setPlaca(
        placa: String,
        id: Int
    ): Result<Boolean>

    suspend fun setVeiculo(
        veiculo: String,
        id: Int
    ): Result<Boolean>

    suspend fun setSent(id: Int): Result<Boolean>
    suspend fun setSend(id: Int): Result<Boolean>
}