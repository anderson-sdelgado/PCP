package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioEquipSegDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioEquipSegRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovEquipProprioEquipSegRoomDatasource @Inject constructor(
    private val movEquipProprioEquipSegDao: MovEquipProprioEquipSegDao
) : MovEquipProprioEquipSegRoomDatasource {

    override suspend fun add(idEquip: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            movEquipProprioEquipSegDao.insert(
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = id,
                    idEquip = idEquip
                )
            )
        }

    override suspend fun addAll(list: List<MovEquipProprioEquipSegRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            movEquipProprioEquipSegDao.insertAll(list)
        }

    override suspend fun delete(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            movEquipProprioEquipSegDao.deleteByIdMov(id)
        }

    override suspend fun delete(idEquip: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            movEquipProprioEquipSegDao.deleteByIdMovAndIdEquip(id, idEquip)
        }

    override suspend fun list(id: Int): Result<List<MovEquipProprioEquipSegRoomModel>> =
        result(getClassAndMethod()) {
            movEquipProprioEquipSegDao.listById(id)
        }

}