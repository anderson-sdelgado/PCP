package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercPassagDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercPassagDatasourceRoom
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import javax.inject.Inject

class MovEquipVisitTercPassagDatasourceRoomImpl @Inject constructor (
    private val movEquipVisitTercPassagDao: MovEquipVisitTercPassagDao,
): MovEquipVisitTercPassagDatasourceRoom {

    override suspend fun addAllMovEquipVisitTercPassag(vararg movEquipVisitTercPassagRoomModels: MovEquipVisitTercPassagRoomModel): Boolean {
        try {
            movEquipVisitTercPassagDao.insertAll(*movEquipVisitTercPassagRoomModels)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun addMovEquipVisitTercPassag(movEquipVisitTercPassagRoomModel: MovEquipVisitTercPassagRoomModel): Boolean {
        try {
            movEquipVisitTercPassagDao.insert(movEquipVisitTercPassagRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun deleteMovEquipVisitTercPassag(movEquipVisitTercPassagRoomModel: MovEquipVisitTercPassagRoomModel): Boolean {
        try {
            movEquipVisitTercPassagDao.delete(movEquipVisitTercPassagRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun listMovEquipVisitTercPassagIdMov(idMov: Long): List<MovEquipVisitTercPassagRoomModel> {
        return movEquipVisitTercPassagDao.listMovEquipVisitTercPassagIdMov(idMov)
    }

}