package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend

class IMovChaveRoomDatasource(
    private val movChaveDao: MovChaveDao
): MovChaveRoomDatasource {

    override suspend fun checkOpen(): Result<Boolean> {
        return try {
            Result.success(
                movChaveDao.listStatusData(StatusData.OPEN).isNotEmpty()
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
                movChaveDao.listStatusSend(StatusSend.SEND).isNotEmpty()
            )
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "IMovEquipProprioRoomDatasource.checkSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun get(id: Int): Result<MovChaveRoomModel> {
        return try {
            Result.success(
                movChaveDao.get(id)
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun listInside(): Result<List<MovChaveRoomModel>> {
        return try {
            Result.success(
                movChaveDao.listStatusForeigner(
                    statusForeigner = StatusForeigner.INSIDE
                )
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.listRemove",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovChaveRoomModel>> {
        return try {
            Result.success(
                movChaveDao.listStatusData(
                    status = StatusData.OPEN
                )
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun listSend(): Result<List<MovChaveRoomModel>> {
        return try {
            Result.success(
                movChaveDao.listStatusSend(StatusSend.SEND)
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.listSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(movChaveRoomModel: MovChaveRoomModel): Result<Long> {
        return try {
            Result.success(
                movChaveDao.insert(movChaveRoomModel)
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.save",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        try {
            val roomModel = movChaveDao.get(id)
            roomModel.statusMovChave = StatusData.CLOSE
            movChaveDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.setClose",
                    cause = e
                )
            )
        }
    }

    override suspend fun setIdChave(
        idChave: Int,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movChaveDao.get(id)
            roomModel.idChaveMovChave = idChave
            roomModel.statusSendMovChave = StatusSend.SEND
            movChaveDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.setIdChave",
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
            val roomModel = movChaveDao.get(id)
            roomModel.observMovChave = observ
            roomModel.statusSendMovChave = StatusSend.SEND
            movChaveDao.update(roomModel)
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
            val roomModel = movChaveDao.get(id)
            roomModel.matricColabMovChave = matric
            roomModel.statusSendMovChave = StatusSend.SEND
            movChaveDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.setMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun setSent(id: Int): Result<Boolean> {
        try {
            val roomModel = movChaveDao.get(id)
            roomModel.statusSendMovChave = StatusSend.SENT
            movChaveDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.setSent",
                    cause = e
                )
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        try {
            val roomModel = movChaveDao.get(id)
            roomModel.statusForeignerMovChave = StatusForeigner.OUTSIDE
            movChaveDao.update(roomModel)
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