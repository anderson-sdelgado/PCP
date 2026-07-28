package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioPassagDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioPassagRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovEquipProprioPassagRoomDatasource @Inject constructor(
    private val movEquipProprioPassagDao: MovEquipProprioPassagDao
) : MovEquipProprioPassagRoomDatasource {

    override suspend fun add(matricColab: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            movEquipProprioPassagDao.insert(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = id,
                    matricColab = matricColab
                )
            )
        }

    override suspend fun addAll(list: List<MovEquipProprioPassagRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            movEquipProprioPassagDao.insertAll(list)
        }

    override suspend fun delete(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            movEquipProprioPassagDao.deleteByIdMov(id)
        }

    override suspend fun delete(matricColab: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            movEquipProprioPassagDao.deleteByIdMovAndMatric(id, matricColab)
        }

    override suspend fun list(id: Int): Result<List<MovEquipProprioPassagRoomModel>> =
        result(getClassAndMethod()) {
            movEquipProprioPassagDao.listById(id)
        }

}