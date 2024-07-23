package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.external.room.dao.stable.LocalDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel
import javax.inject.Inject

class LocalRoomDatasourceImpl @Inject constructor (
    private val localDao: LocalDao,
): LocalRoomDatasource {

    override suspend fun addAllLocal(vararg localRoomModels: LocalRoomModel) {
        localDao.insertAll(*localRoomModels)
    }

    override suspend fun deleteAllLocal() {
        localDao.deleteAll()
    }

    override suspend fun listAllLocal(): List<LocalRoomModel> {
        return localDao.listAll()
    }

    override suspend fun getLocalId(id: Long): LocalRoomModel {
        return localDao.getLocalId(id)
    }

}