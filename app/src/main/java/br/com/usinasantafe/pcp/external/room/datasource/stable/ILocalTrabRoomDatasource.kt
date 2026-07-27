package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.external.room.dao.stable.LocalTrabDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalTrabRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalTrabRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class ILocalTrabRoomDatasource @Inject constructor(
    private val localTrabDao: LocalTrabDao
): LocalTrabRoomDatasource {

    override suspend fun addAll(list: List<LocalTrabRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            localTrabDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            localTrabDao.deleteAll()
        }

    override suspend fun getDescrById(id: Int): Result<String> =
        result(getClassAndMethod()) {
            localTrabDao.getDescrById(id)
        }

}