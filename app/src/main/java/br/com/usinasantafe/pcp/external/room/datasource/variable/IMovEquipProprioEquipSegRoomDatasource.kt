package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioEquipSegDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioEquipSegRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel

class IMovEquipProprioEquipSegRoomDatasource(
    private val movEquipProprioEquipSegDao: MovEquipProprioEquipSegDao
) : MovEquipProprioEquipSegRoomDatasource {

    override suspend fun add(idEquip: Int, id: Int): Result<Boolean> {
        try {
            movEquipProprioEquipSegDao.insert(
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = id,
                    idEquip = idEquip
                )
            )
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioEquipSegRoomDatasource.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun addAll(list: List<MovEquipProprioEquipSegRoomModel>): Result<Boolean> {
        try {
            movEquipProprioEquipSegDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioEquipSegRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        try {
            val list = movEquipProprioEquipSegDao.get(id)
            for(mov in list) {
                movEquipProprioEquipSegDao.delete(mov)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioEquipSegRoomDatasource.delete(id)",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun delete(idEquip: Int, id: Int): Result<Boolean> {
        try {
            val mov = movEquipProprioEquipSegDao.get(id, idEquip)
            movEquipProprioEquipSegDao.delete(mov)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioEquipSegRoomDatasource.delete(idEquip, id)",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun list(id: Int): Result<List<MovEquipProprioEquipSegRoomModel>> {
        return try {
            Result.success(movEquipProprioEquipSegDao.list(id))
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioEquipSegRoomDatasource.list",
                message = "-",
                cause = e
            )
        }
    }

}