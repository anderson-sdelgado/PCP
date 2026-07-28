package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IEquipRoomDatasource @Inject constructor(
    private val equipDao: EquipDao
) : EquipRoomDatasource {

    override suspend fun addAll(list: List<EquipRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            equipDao.insertAll(list)
        }

    override suspend fun hasNro(nroEquip: Long): Result<Boolean> =
        result(getClassAndMethod()) {
            equipDao.checkNro(nroEquip) > 0
        }

    override suspend fun getById(idEquip: Int): Result<EquipRoomModel> =
        result(getClassAndMethod()) {
            equipDao.getById(idEquip)
        }

    override suspend fun getIdByNro(nroEquip: Long): Result<Int> =
        result(getClassAndMethod()) {
            equipDao.getIdByNro(nroEquip)
        }

    override suspend fun getNroById(idEquip: Int): Result<Long> =
        result(getClassAndMethod()) {
            equipDao.getNroById(idEquip)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            equipDao.deleteAll()
        }

}