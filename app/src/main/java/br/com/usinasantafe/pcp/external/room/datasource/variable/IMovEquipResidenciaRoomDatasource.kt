package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend

class IMovEquipResidenciaRoomDatasource(
    private val movEquipResidenciaDao: MovEquipResidenciaDao
): MovEquipResidenciaRoomDatasource {

    override suspend fun checkOpen(): Result<Boolean> {
        return try {
            Result.success(
                movEquipResidenciaDao.listStatusData(StatusData.OPEN).isNotEmpty()
            )
        } catch (e: Exception){
            resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.checkOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkSend(): Result<Boolean> {
        return try {
            Result.success(
                movEquipResidenciaDao.listStatusSend(StatusSend.SEND).isNotEmpty()
            )
        } catch (e: Exception){
            resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.checkSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun delete(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Boolean> {
        try {
            movEquipResidenciaDao.delete(movEquipResidenciaRoomModel)
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.delete",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(id: Int): Result<MovEquipResidenciaRoomModel> {
        return try {
            Result.success(movEquipResidenciaDao.get(id))
        } catch (e: Exception){
            resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipResidenciaRoomModel>> {
        try{
            val list = movEquipResidenciaDao.listStatusData(StatusData.OPEN)
            return Result.success(list)
        } catch (e: Exception){
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.listOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listInside(): Result<List<MovEquipResidenciaRoomModel>> {
        try {
            val list = movEquipResidenciaDao.listStatusForeigner(
                statusForeigner = StatusForeigner.INSIDE
            )
            return Result.success(list)
        } catch (e: Exception){
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.listInside",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSend(): Result<List<MovEquipResidenciaRoomModel>> {
        try {
            val list = movEquipResidenciaDao.listStatusSend(StatusSend.SEND)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.listSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSent(): Result<List<MovEquipResidenciaRoomModel>> {
        try {
            val list = movEquipResidenciaDao.listStatusSend(StatusSend.SENT)
            return Result.success(list)
        } catch (e: Exception){
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.listSent",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Long> {
        try {
            val id = movEquipResidenciaDao.insert(movEquipResidenciaRoomModel)
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.statusMovEquipResidencia = StatusData.CLOSE
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.setClose",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setMotorista(
        motorista: String,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.motoristaMovEquipResidencia = motorista
            roomModel.statusSendMovEquipResidencia= StatusSend.SEND
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.setMotorista",
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
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.observMovEquipResidencia = observ
            roomModel.statusSendMovEquipResidencia= StatusSend.SEND
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.setObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.statusMovEquipForeignerResidencia = StatusForeigner.OUTSIDE
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.setOutside",
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
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.placaMovEquipResidencia = placa
            roomModel.statusSendMovEquipResidencia= StatusSend.SEND
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.setPlaca",
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
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.veiculoMovEquipResidencia = veiculo
            roomModel.statusSendMovEquipResidencia= StatusSend.SEND
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.setVeiculo",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSent(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.statusSendMovEquipResidencia= StatusSend.SENT
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRoomDatasource.setSent",
                message = "-",
                cause = e
            )
        }
    }

}