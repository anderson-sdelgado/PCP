package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.LocalDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel

class ILocalRoomDatasource(
    private val localDao: LocalDao
): LocalRoomDatasource {

    override suspend fun addAll(list: List<LocalRoomModel>): Result<Boolean> {
        try {
            localDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ILocalRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }


    override suspend fun getDescr(id: Int): Result<String> {
        try {
            val descrLocal = localDao.getDescr(id)
            return Result.success(descrLocal)
        } catch (e: Exception) {
            return resultFailure(
                context = "ILocalRoomDatasource.getDescr",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            localDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ILocalRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listAll(): Result<List<LocalRoomModel>> {
        return try {
            Result.success(localDao.listAll())
        } catch (e: Exception) {
            return resultFailure(
                context = "ILocalRoomDatasource.listAll",
                message = "-",
                cause = e
            )
        }
    }
}