package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
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
            return Result.failure(
                DatasourceException(
                    function = "ILocalTrabRoomDatasource.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            localTrabDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "ILocalTrabRoomDatasource.deleteAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun getDescr(id: Int): Result<String> {
        try {
            val model = localTrabDao.get(id)
            return Result.success(model.descrLocalTrab)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "ILocalTrabRoomDatasource.getDescr",
                    cause = e
                )
            )
        }
    }

}