package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel

class IEquipRoomDatasource(
    private val equipDao: EquipDao
) : EquipRoomDatasource {

    override suspend fun addAll(list: List<EquipRoomModel>): Result<Boolean> {
        try {
            equipDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkNro(nroEquip: Long): Result<Boolean> {
        try {
            val result = equipDao.checkNro(nroEquip) > 0
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRoomDatasource.checkNro",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(idEquip: Int): Result<EquipRoomModel> {
        return try {
            val result = equipDao.get(idEquip)
            Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRoomDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getId(nroEquip: Long): Result<Int> {
        return try {
            Result.success(equipDao.getId(nroEquip))
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRoomDatasource.getId",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getNro(idEquip: Int): Result<Long> {
        return try {
            val result = equipDao.getNro(idEquip)
            Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRoomDatasource.getNro",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            equipDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

}