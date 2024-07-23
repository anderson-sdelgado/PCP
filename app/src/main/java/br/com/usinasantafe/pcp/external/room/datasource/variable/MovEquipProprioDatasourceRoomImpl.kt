package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioDatasourceRoom
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import javax.inject.Inject

class MovEquipProprioDatasourceRoomImpl @Inject constructor(
    private val movEquipProprioDao: MovEquipProprioDao
) : MovEquipProprioDatasourceRoom {

    override suspend fun checkMovSend(): Boolean {
        return movEquipProprioDao.listMovStatusEnvio(StatusSend.SEND).isNotEmpty()
    }

    override suspend fun deleteMov(movEquipProprioRoomModel: MovEquipProprioRoomModel): Boolean {
        try {
            movEquipProprioDao.delete(movEquipProprioRoomModel)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun updateStatusMovEquipProprioClose(movEquipProprioRoomModel: MovEquipProprioRoomModel): Boolean {
        try {
            movEquipProprioRoomModel.statusMovEquipProprio = StatusData.CLOSE
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun lastIdMovStatusOpen(): Long {
        return movEquipProprioDao.lastIdMovStatus(StatusData.OPEN)
    }

    override suspend fun listMovEquipProprioOpen(): List<MovEquipProprioRoomModel> {
        return movEquipProprioDao.listMovStatus(StatusData.OPEN)
    }

    override suspend fun listMovEquipProprioSend(): List<MovEquipProprioRoomModel> {
        return movEquipProprioDao.listMovStatusEnvio(StatusSend.SEND)
    }

    override suspend fun listMovEquipProprioSent(): List<MovEquipProprioRoomModel> {
        return movEquipProprioDao.listMovStatusEnvio(StatusSend.SENT)
    }

    override suspend fun saveMovEquipProprioOpen(movEquipProprioRoomModel: MovEquipProprioRoomModel): Boolean {
        try {
            movEquipProprioDao.insert(movEquipProprioRoomModel)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun updateStatusSendMovEquipProprio(movEquipProprioRoomModel: MovEquipProprioRoomModel): Boolean {
        try {
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun updateStatusMovEquipProprioSent(idMov: Long): Boolean {
        try {
            val movEquip = movEquipProprioDao.listMovId(idMov).single()
            movEquip.statusSendMovEquipProprio = StatusSend.SENT
            movEquipProprioDao.update(movEquip)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun updateDestinoMovEquipProprio(
        destino: String,
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean {
        try {
            movEquipProprioRoomModel.destinoMovEquipProprio = destino
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun updateIdEquipMovEquipProprio(
        idEquip: Long,
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean {
        try {
            movEquipProprioRoomModel.idEquipMovEquipProprio = idEquip
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun updateNroColabMovEquipProprio(
        nroMatric: Long,
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean {
        try {
            movEquipProprioRoomModel.nroMatricColabMovEquipProprio = nroMatric
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun updateNotaFiscalMovEquipProprio(
        notaFiscal: Long,
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean {
        try {
            movEquipProprioRoomModel.nroNotaFiscalMovEquipProprio = notaFiscal
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun updateObservMovEquipProprio(
        observ: String?,
        movEquipProprioRoomModel: MovEquipProprioRoomModel
    ): Boolean {
        try {
            movEquipProprioRoomModel.observMovEquipProprio = observ
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

}