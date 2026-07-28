package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercPassagDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercPassagRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovEquipVisitTercPassagRoomDatasource @Inject constructor(
    private val movEquipVisitTercPassagDao: MovEquipVisitTercPassagDao
) : MovEquipVisitTercPassagRoomDatasource {

    override suspend fun add(idVisitTerc: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = id,
                    idVisitTerc = idVisitTerc
                )
            )
        }

    override suspend fun addAll(list: List<MovEquipVisitTercPassagRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            movEquipVisitTercPassagDao.insertAll(list)
        }

    override suspend fun delete(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            movEquipVisitTercPassagDao.deleteByIdMov(id)
        }

    override suspend fun delete(idVisitTerc: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            movEquipVisitTercPassagDao.deleteByIdMovAndIdVisitTerc(idVisitTerc, id)
        }

    override suspend fun list(id: Int): Result<List<MovEquipVisitTercPassagRoomModel>> =
        result(getClassAndMethod()) {
            movEquipVisitTercPassagDao.list(id)
        }

}