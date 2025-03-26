package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.LocalTrabDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalTrabRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalTrabRoomModel

class ILocalTrabRoomDatasource(
    private val localTrabDao: LocalTrabDao
): LocalTrabRoomDatasource {

    override suspend fun addAll(list: List<LocalTrabRoomModel>): Result<Boolean> {
        try {
            localTrabDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ILocalTrabRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            localTrabDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ILocalTrabRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getDescr(id: Int): Result<String> {
        try {
            val model = localTrabDao.get(id)
            return Result.success(model.descrLocalTrab)
        } catch (e: Exception) {
            return resultFailure(
                context = "ILocalTrabRoomDatasource.getDescr",
                message = "-",
                cause = e
            )
        }
    }

}