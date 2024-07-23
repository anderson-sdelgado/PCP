package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioPassagDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioPassagDatasourceRoom
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import javax.inject.Inject

class MovEquipProprioPassagDatasourceRoomImpl @Inject constructor (
    private val movEquipProprioPassagDao: MovEquipProprioPassagDao,
): MovEquipProprioPassagDatasourceRoom {

    override suspend fun addAllMovEquipProprioPassag(vararg movEquipProprioPassagRoomModels: MovEquipProprioPassagRoomModel): Boolean {
        try {
            movEquipProprioPassagDao.insertAll(*movEquipProprioPassagRoomModels)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun addMovEquipProprioPassag(movEquipProprioPassagRoomModel: MovEquipProprioPassagRoomModel): Boolean {
        try {
            movEquipProprioPassagDao.insert(movEquipProprioPassagRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun listMovEquipProprioPassagIdMov(idMov: Long): List<MovEquipProprioPassagRoomModel> {
        return movEquipProprioPassagDao.listMovEquipProprioPassagIdMov(idMov)
    }

    override suspend fun deleteMovEquipProprioPassag(movEquipProprioPassagRoomModel: MovEquipProprioPassagRoomModel): Boolean {
        try {
            movEquipProprioPassagDao.delete(movEquipProprioPassagRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

}