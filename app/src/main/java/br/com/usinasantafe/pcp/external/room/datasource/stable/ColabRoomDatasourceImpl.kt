package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import javax.inject.Inject

class ColabRoomDatasourceImpl @Inject constructor (
    private val colabDao: ColabDao,
): ColabRoomDatasource {

    override suspend fun addAllColab(vararg colabRoomModels: ColabRoomModel) {
        colabDao.insertAll(*colabRoomModels)
    }

    override suspend fun checkColabMatric(matric: Long): Boolean {
        return colabDao.checkColabMatric(matric) > 0
    }

    override suspend fun deleteAllColab() {
        colabDao.deleteAll()
    }

    override suspend fun getColabMatric(matric: Long): ColabRoomModel {
        return colabDao.getColabMatric(matric)
    }

}