package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import javax.inject.Inject

class EquipRoomDatasourceImpl @Inject constructor (
    private val equipDao: EquipDao,
): EquipRoomDatasource {

    override suspend fun addAllEquip(vararg equipRoomModels: EquipRoomModel) {
        equipDao.insertAll(*equipRoomModels)
    }

    override suspend fun deleteAllEquip() {
        equipDao.deleteAll()
    }

    override suspend fun checkEquipNro(nro: Long): Boolean {
        return equipDao.checkEquipNro(nro) > 0
    }

    override suspend fun getEquipNro(nro: Long): EquipRoomModel {
        return equipDao.getEquipNro(nro)
    }

    override suspend fun getEquipId(id: Long): EquipRoomModel {
        return equipDao.getEquipId(id)
    }

}