package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
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
            Result.failure(
                DatasourceException(
                    function = "IMovEquipProprioRoomDatasource.checkOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun checkSend(): Result<Boolean> {
        return try {
            Result.success(
                movChaveEquipDao.listStatusSend(StatusSend.SEND).isNotEmpty()
            )
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "IMovEquipProprioRoomDatasource.checkOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun get(id: Int): Result<MovChaveEquipRoomModel> {
        return try {
            Result.success(
                movChaveEquipDao.get(id)
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.get",
                    cause = e
                )
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
            Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.listRemove",
                    cause = e
                )
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
            Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.listOpen",
                    cause = e
                )
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
            Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.listSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(movChaveEquipRoomModel: MovChaveEquipRoomModel): Result<Long> {
        return try {
            Result.success(
                movChaveEquipDao.insert(movChaveEquipRoomModel)
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.save",
                    cause = e
                )
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
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.setClose",
                    cause = e
                )
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
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.setIdEquip",
                    cause = e
                )
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
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.setObserv",
                    cause = e
                )
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
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.setMatricColab",
                    cause = e
                )
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
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.setSent",
                    cause = e
                )
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
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.setOutside",
                    cause = e
                )
            )
        }
    }


}