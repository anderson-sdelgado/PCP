package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend

class IMovEquipVisitTercRoomDatasource(
    private val movEquipVisitTercDao: MovEquipVisitTercDao
) : MovEquipVisitTercRoomDatasource {

    override suspend fun checkOpen(): Result<Boolean> {
        return try {
            Result.success(
                movEquipVisitTercDao.listStatusData(StatusData.OPEN).isNotEmpty()
            )
        } catch (e: Exception){
            resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.checkOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkSend(): Result<Boolean> {
        return try {
            Result.success(
                movEquipVisitTercDao.listStatusSend(StatusSend.SEND).isNotEmpty()
            )
        } catch (e: Exception) {
            resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.checkSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun delete(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Boolean> {
        try {
            movEquipVisitTercDao.delete(movEquipVisitTercRoomModel)
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.delete",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(id: Int): Result<MovEquipVisitTercRoomModel> {
        return try {
            Result.success(movEquipVisitTercDao.get(id))
        } catch (e: Exception) {
            resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipVisitTercRoomModel>> {
        try {
            val list = movEquipVisitTercDao.listStatusData(StatusData.OPEN)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.listOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listInside(): Result<List<MovEquipVisitTercRoomModel>> {
        try {
            val list = movEquipVisitTercDao.listStatusForeigner(
                statusForeigner = StatusForeigner.INSIDE
            )
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.listInside",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSend(): Result<List<MovEquipVisitTercRoomModel>> {
        try {
            val listOpen = movEquipVisitTercDao.listStatusSend(StatusSend.SEND)
            return Result.success(listOpen)
        } catch (e: Exception){
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.listSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSent(): Result<List<MovEquipVisitTercRoomModel>> {
        try {
            val listOpen = movEquipVisitTercDao.listStatusSend(StatusSend.SENT)
            return Result.success(listOpen)
        } catch (e: Exception){
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.listSent",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Long> {
        try {
            val id = movEquipVisitTercDao.insert(movEquipVisitTercRoomModel)
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.statusMovEquipVisitTerc = StatusData.CLOSE
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.setClose",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setDestino(
        destino: String,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.destinoMovEquipVisitTerc = destino
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.setDestino",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setIdVisitTerc(
        idVisitTerc: Int,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.idVisitTercMovEquipVisitTerc = idVisitTerc
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.setIdVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setObserv(
        observ: String?,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.observMovEquipVisitTerc = observ
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.setObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.statusMovEquipForeigVisitTerc = StatusForeigner.OUTSIDE
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.setOutside",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setPlaca(
        placa: String,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.placaMovEquipVisitTerc = placa
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.setPlaca",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setVeiculo(
        veiculo: String,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.veiculoMovEquipVisitTerc = veiculo
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.setVeiculo",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSent(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SENT
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.setSent",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSend(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRoomDatasource.setSend",
                message = "-",
                cause = e
            )
        }
    }

}