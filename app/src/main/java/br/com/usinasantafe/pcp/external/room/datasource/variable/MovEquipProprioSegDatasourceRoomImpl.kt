package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioSegDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioSegDatasourceRoom
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioSegRoomModel
import javax.inject.Inject

class MovEquipProprioSegDatasourceRoomImpl @Inject constructor (
    private val movEquipProprioSegDao: MovEquipProprioSegDao,
): MovEquipProprioSegDatasourceRoom {

    override suspend fun addAllMovEquipProprioSeg(vararg movEquipProprioSegRoomModels: MovEquipProprioSegRoomModel): Boolean {
        try {
            movEquipProprioSegDao.insertAll(*movEquipProprioSegRoomModels)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun addMovEquipProprioSeg(
        movEquipProprioSegRoomModel: MovEquipProprioSegRoomModel
    ): Boolean {
        try {
            movEquipProprioSegDao.insert(movEquipProprioSegRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun listMovEquipProprioSegIdMov(idMov: Long): List<MovEquipProprioSegRoomModel> {
        return movEquipProprioSegDao.listMovEquipProprioSegIdMov(idMov)
    }

    override suspend fun deleteMovEquipProprioSeg(movEquipProprioSegRoomModel: MovEquipProprioSegRoomModel): Boolean {
        try {
            movEquipProprioSegDao.delete(movEquipProprioSegRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

}