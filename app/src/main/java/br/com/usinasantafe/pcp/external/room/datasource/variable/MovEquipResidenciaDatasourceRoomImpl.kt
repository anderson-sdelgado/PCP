package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipResidenciaDatasourceRoom
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.utils.StatusForeigner
import javax.inject.Inject

class MovEquipResidenciaDatasourceRoomImpl @Inject constructor (
    private val movEquipResidenciaDao: MovEquipResidenciaDao,
): MovEquipResidenciaDatasourceRoom {

    override suspend fun checkMovSend(): Boolean {
        return movEquipResidenciaDao.listMovStatusEnvio(StatusSend.SEND).isNotEmpty()
    }

    override suspend fun deleteMovEquipResidencia(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Boolean {
        try {
            movEquipResidenciaDao.delete(movEquipResidenciaRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun lastIdMovStatusOpen(): Long {
        return movEquipResidenciaDao.lastIdMovStatus(StatusData.OPEN)
    }

    override suspend fun listMovEquipResidenciaInside(): List<MovEquipResidenciaRoomModel> {
        return movEquipResidenciaDao.listMovStatusForeigner(StatusForeigner.INSIDE)
    }

    override suspend fun listMovEquipResidenciaOpen(): List<MovEquipResidenciaRoomModel> {
        return movEquipResidenciaDao.listMovStatus(StatusData.OPEN)
    }

    override suspend fun listMovEquipResidenciaSend(): List<MovEquipResidenciaRoomModel> {
        return movEquipResidenciaDao.listMovStatusEnvio(StatusSend.SEND)
    }

    override suspend fun listMovEquipResidenciaSent(): List<MovEquipResidenciaRoomModel> {
        return movEquipResidenciaDao.listMovStatusEnvio(StatusSend.SENT)
    }

    override suspend fun updateVeiculoMovEquipResidencia(
        veiculo: String,
        movEquipVisitTercRoomModel: MovEquipResidenciaRoomModel
    ): Boolean {
        try {
            movEquipVisitTercRoomModel.veiculoMovEquipResidencia = veiculo
            movEquipVisitTercRoomModel.statusSendMovEquipResidencia = StatusSend.SEND
            movEquipResidenciaDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updatePlacaMovEquipResidencia(
        placa: String,
        movEquipVisitTercRoomModel: MovEquipResidenciaRoomModel
    ): Boolean {
        try {
            movEquipVisitTercRoomModel.placaMovEquipResidencia = placa
            movEquipVisitTercRoomModel.statusSendMovEquipResidencia = StatusSend.SEND
            movEquipResidenciaDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateMotoristaMovEquipResidencia(
        motorista: String,
        movEquipVisitTercRoomModel: MovEquipResidenciaRoomModel
    ): Boolean {
        try {
            movEquipVisitTercRoomModel.motoristaMovEquipResidencia = motorista
            movEquipVisitTercRoomModel.statusSendMovEquipResidencia = StatusSend.SEND
            movEquipResidenciaDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateObservMovEquipResidencia(
        observ: String?,
        movEquipVisitTercRoomModel: MovEquipResidenciaRoomModel
    ): Boolean {
        try {
            movEquipVisitTercRoomModel.observMovEquipResidencia = observ
            movEquipVisitTercRoomModel.statusSendMovEquipResidencia = StatusSend.SEND
            movEquipResidenciaDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun insertMovEquipResidenciaOpen(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Boolean {
        try {
            movEquipResidenciaDao.insert(movEquipResidenciaRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun insertMovEquipResidenciaOutside(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Boolean {
        try {
            movEquipResidenciaRoomModel.idMovEquipResidencia = null
            movEquipResidenciaRoomModel.statusMovEquipForeigResidencia = StatusForeigner.OUTSIDE
            movEquipResidenciaRoomModel.statusMovEquipResidencia = StatusData.OPEN
            movEquipResidenciaRoomModel.statusSendMovEquipResidencia = StatusSend.SEND
            movEquipResidenciaDao.insert(movEquipResidenciaRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateStatusSentMovEquipResidencia(idMov: Long): Boolean {
        try {
            val movEquip = movEquipResidenciaDao.listMovId(idMov).single()
            movEquip.statusSendMovEquipResidencia = StatusSend.SENT
            movEquipResidenciaDao.update(movEquip)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateStatusMovEquipResidenciaOutside(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Boolean {
        try {
            movEquipResidenciaRoomModel.statusMovEquipForeigResidencia = StatusForeigner.OUTSIDE
            movEquipResidenciaDao.update(movEquipResidenciaRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateStatusMovEquipResidenciaClose(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Boolean {
        try {
            movEquipResidenciaRoomModel.statusMovEquipResidencia = StatusData.CLOSE
            movEquipResidenciaDao.update(movEquipResidenciaRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

}