package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveEquipDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveEquipRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend

class IMovChaveEquipRoomDatasource(
    private val movChaveEquipDao: MovChaveEquipDao
): MovChaveEquipRoomDatasource {

    override suspend fun checkOpen(): Result<Boolean> {
        return try {
            Result.success(
                movChaveEquipDao.listStatusData(StatusData.OPEN).isNotEmpty()
            )
        } catch (e: Exception){
            resultFailure(
                context = "IMovChaveEquipRoomDatasource.checkOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkSend(): Result<Boolean> {
        return try {
            Result.success(
                movChaveEquipDao.listStatusSend(StatusSend.SEND).isNotEmpty()
            )
        } catch (e: Exception){
            resultFailure(
                context = "IMovChaveEquipRoomDatasource.checkSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(id: Int): Result<MovChaveEquipRoomModel> {
        return try {
            Result.success(
                movChaveEquipDao.get(id)
            )
        } catch (e: Exception) {
            resultFailure(
                context = "IMovChaveEquipRoomDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listInside(): Result<List<MovChaveEquipRoomModel>> {
        return try {
            Result.success(
                movChaveEquipDao.listStatusForeigner(
                    statusForeigner = StatusForeigner.INSIDE
                )
            )
        } catch (e: Exception) {
            resultFailure(
                context = "IMovChaveEquipRoomDatasource.listInside",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovChaveEquipRoomModel>> {
        return try {
            Result.success(
                movChaveEquipDao.listStatusData(
                    status = StatusData.OPEN
                )
            )
        } catch (e: Exception) {
            resultFailure(
                context = "IMovChaveEquipRoomDatasource.listOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSend(): Result<List<MovChaveEquipRoomModel>> {
        return try {
            Result.success(
                movChaveEquipDao.listStatusSend(
                    statusSend = StatusSend.SEND
                )
            )
        } catch (e: Exception) {
            resultFailure(
                context = "IMovChaveEquipRoomDatasource.listSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(movChaveEquipRoomModel: MovChaveEquipRoomModel): Result<Long> {
        return try {
            Result.success(
                movChaveEquipDao.insert(movChaveEquipRoomModel)
            )
        } catch (e: Exception) {
            resultFailure(
                context = "IMovChaveEquipRoomDatasource.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        try {
            val roomModel = movChaveEquipDao.get(id)
            roomModel.statusMovChaveEquip = StatusData.CLOSE
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRoomDatasource.setClose",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setIdEquip(
        idEquip: Int,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movChaveEquipDao.get(id)
            roomModel.idEquipMovChaveEquip = idEquip
            roomModel.statusSendMovChaveEquip = StatusSend.SEND
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRoomDatasource.setIdEquip",
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
            val roomModel = movChaveEquipDao.get(id)
            roomModel.observMovChaveEquip = observ
            roomModel.statusSendMovChaveEquip = StatusSend.SEND
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRoomDatasource.setObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setMatricColab(
        matric: Int,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movChaveEquipDao.get(id)
            roomModel.matricColabMovChaveEquip = matric
            roomModel.statusSendMovChaveEquip = StatusSend.SEND
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRoomDatasource.setMatricColab",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSent(id: Int): Result<Boolean> {
        try {
            val roomModel = movChaveEquipDao.get(id)
            roomModel.statusSendMovChaveEquip = StatusSend.SENT
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRoomDatasource.setSent",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        try {
            val roomModel = movChaveEquipDao.get(id)
            roomModel.statusForeignerMovChaveEquip = StatusForeigner.OUTSIDE
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRoomDatasource.setOutside",
                message = "-",
                cause = e
            )
        }
    }


}