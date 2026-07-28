package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovEquipProprioRoomDatasource @Inject constructor(
    private val movEquipProprioDao: MovEquipProprioDao
): MovEquipProprioRoomDatasource {

    suspend fun updateModel(id: Int, block: MovEquipProprioRoomModel.() -> Unit) {
        val model = get(id).getOrThrow()
        model.block()
        update(model).getOrThrow()
    }

    override suspend fun checkOpen(): Result<Boolean> =
        result(getClassAndMethod()) {
            movEquipProprioDao.hasByStatusData(StatusData.OPEN)
        }

    override suspend fun checkSend(): Result<Boolean> =
        result(getClassAndMethod()) {
            movEquipProprioDao.hasByStatusSend(StatusSend.SEND)
        }

    override suspend fun delete(movEquipProprioRoomModel: MovEquipProprioRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            movEquipProprioDao.delete(movEquipProprioRoomModel)
        }

    override suspend fun get(id: Int): Result<MovEquipProprioRoomModel> =
        result(getClassAndMethod()) {
            movEquipProprioDao.get(id)
        }

    override suspend fun listOpen(): Result<List<MovEquipProprioRoomModel>> =
        result(getClassAndMethod()) {
            movEquipProprioDao.listByStatusData(StatusData.OPEN)
        }

    override suspend fun listSend(): Result<List<MovEquipProprioRoomModel>> =
        result(getClassAndMethod()) {
            movEquipProprioDao.listByStatusSend(StatusSend.SEND)
        }

    override suspend fun listSent(): Result<List<MovEquipProprioRoomModel>> =
        result(getClassAndMethod()) {
            movEquipProprioDao.listByStatusSend(StatusSend.SENT)
        }

    override suspend fun save(movEquipProprioRoomModel: MovEquipProprioRoomModel): Result<Long> =
        result(getClassAndMethod()) {
            movEquipProprioDao.insert(movEquipProprioRoomModel)
        }

    override suspend fun setClose(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusMovEquipProprio = StatusData.CLOSE
            }
        }

    override suspend fun setDestino(destino: String, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.destinoMovEquipProprio = destino
                this.statusSendMovEquipProprio = StatusSend.SEND
            }
        }

    override suspend fun setIdEquip(idEquip: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.idEquipMovEquipProprio = idEquip
                this.statusSendMovEquipProprio = StatusSend.SEND
            }
        }

    override suspend fun setMatricColab(matricColab: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.matricColabMovEquipProprio = matricColab
                this.statusSendMovEquipProprio = StatusSend.SEND
            }
        }

    override suspend fun setNotaFiscal(notaFiscal: Int?, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.notaFiscalMovEquipProprio = notaFiscal
                this.statusSendMovEquipProprio = StatusSend.SEND
            }
        }

    override suspend fun setObserv(observ: String?, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.observMovEquipProprio = observ
                this.statusSendMovEquipProprio = StatusSend.SEND
            }
        }

    override suspend fun setSent(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusSendMovEquipProprio = StatusSend.SENT
            }
        }

    override suspend fun setSend(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusSendMovEquipProprio = StatusSend.SEND
            }
        }

    suspend fun update(model: MovEquipProprioRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            movEquipProprioDao.update(model)
        }
}