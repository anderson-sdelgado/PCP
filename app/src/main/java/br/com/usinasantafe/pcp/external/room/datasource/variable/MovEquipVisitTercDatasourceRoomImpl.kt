package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercDatasourceRoom
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcp.utils.StatusForeigner
import javax.inject.Inject

class MovEquipVisitTercDatasourceRoomImpl @Inject constructor (
    private val movEquipVisitTercDao: MovEquipVisitTercDao,
): MovEquipVisitTercDatasourceRoom {

    override suspend fun checkMovSend(): Boolean {
        return movEquipVisitTercDao.listMovStatusEnvio(StatusSend.SEND).isNotEmpty()
    }

    override suspend fun deleteMovEquipVisitTerc(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Boolean {
        try {
            movEquipVisitTercDao.delete(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun insertMovEquipVisitTercOpen(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Boolean {
        try {
            movEquipVisitTercDao.insert(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun insertMovEquipVisitTercOutside(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Boolean {
        try {
            movEquipVisitTercRoomModel.idMovEquipVisitTerc = null
            movEquipVisitTercRoomModel.statusMovEquipForeigVisitTerc = StatusForeigner.OUTSIDE
            movEquipVisitTercRoomModel.statusMovEquipVisitTerc = StatusData.OPEN
            movEquipVisitTercRoomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.insert(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun lastIdMovStatusOpen(): Long {
        return movEquipVisitTercDao.lastIdMovStatus(StatusData.OPEN)
    }

    override suspend fun listMovEquipVisitTercInside(): List<MovEquipVisitTercRoomModel> {
        return movEquipVisitTercDao.listMovStatusForeigner(StatusForeigner.INSIDE)
    }

    override suspend fun listMovEquipVisitTercOpen(): List<MovEquipVisitTercRoomModel> {
        return movEquipVisitTercDao.listMovStatus(StatusData.OPEN)
    }

    override suspend fun listMovEquipVisitTercSend(): List<MovEquipVisitTercRoomModel> {
        return movEquipVisitTercDao.listMovStatusEnvio(StatusSend.SEND)
    }

    override suspend fun listMovEquipVisitTercSent(): List<MovEquipVisitTercRoomModel> {
        return movEquipVisitTercDao.listMovStatusEnvio(StatusSend.SENT)
    }

    override suspend fun updateVeiculoMovEquipVisitTerc(
        veiculo: String,
        movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel
    ): Boolean {
        try {
            movEquipVisitTercRoomModel.veiculoMovEquipVisitTerc = veiculo
            movEquipVisitTercRoomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updatePlacaMovEquipVisitTerc(
        placa: String,
        movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel
    ): Boolean {
        try {
            movEquipVisitTercRoomModel.placaMovEquipVisitTerc = placa
            movEquipVisitTercRoomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateMotoristaMovEquipVisitTerc(
        idVisitTerc: Long,
        movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel
    ): Boolean {
        try {
            movEquipVisitTercRoomModel.idVisitTercMovEquipVisitTerc = idVisitTerc
            movEquipVisitTercRoomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateDestinoMovEquipVisitTerc(
        destino: String,
        movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel
    ): Boolean {
        try {
            movEquipVisitTercRoomModel.destinoMovEquipVisitTerc = destino
            movEquipVisitTercRoomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateObservMovEquipVisitTerc(
        observ: String?,
        movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel
    ): Boolean {
        try {
            movEquipVisitTercRoomModel.observMovEquipVisitTerc = observ
            movEquipVisitTercRoomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateStatusMovEquipVisitTercSent(idMov: Long): Boolean {
        try {
            val movEquip = movEquipVisitTercDao.listMovId(idMov).single()
            movEquip.statusSendMovEquipVisitTerc = StatusSend.SENT
            movEquipVisitTercDao.update(movEquip)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateStatusMovEquipVisitTercOutSide(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Boolean {
        try {
            movEquipVisitTercRoomModel.statusMovEquipForeigVisitTerc = StatusForeigner.OUTSIDE
            movEquipVisitTercDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateStatusMovEquipVisitTercClose(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Boolean {
        try {
            movEquipVisitTercRoomModel.statusMovEquipVisitTerc = StatusData.CLOSE
            movEquipVisitTercDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }

    override suspend fun updateStatusMovEquipVisitTercSend(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Boolean {
        try {
            movEquipVisitTercRoomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(movEquipVisitTercRoomModel)
            return true
        } catch (exception: Exception){
            return false
        }
    }
}