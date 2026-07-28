package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovEquipVisitTercRoomDatasource @Inject constructor(
    private val movEquipVisitTercDao: MovEquipVisitTercDao
) : MovEquipVisitTercRoomDatasource {

    suspend fun updateModel(id: Int, block: MovEquipVisitTercRoomModel.() -> Unit) {
        val model = get(id).getOrThrow()
        model.block()
        update(model).getOrThrow()
    }

    override suspend fun checkOpen(): Result<Boolean> =
        result(getClassAndMethod()) {
            movEquipVisitTercDao.hasByStatusData(StatusData.OPEN)
        }

    override suspend fun checkSend(): Result<Boolean> =
        result(getClassAndMethod()) {
            movEquipVisitTercDao.hasByStatusSend(StatusSend.SEND)
        }

    override suspend fun delete(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            movEquipVisitTercDao.delete(movEquipVisitTercRoomModel)
        }

    override suspend fun get(id: Int): Result<MovEquipVisitTercRoomModel> =
        result(getClassAndMethod()) {
            movEquipVisitTercDao.get(id)
        }

    override suspend fun listOpen(): Result<List<MovEquipVisitTercRoomModel>> =
        result(getClassAndMethod()) {
            movEquipVisitTercDao.listByStatusData(StatusData.OPEN)
        }

    override suspend fun listInside(): Result<List<MovEquipVisitTercRoomModel>> =
        result(getClassAndMethod()) {
            movEquipVisitTercDao.listByStatusForeigner(StatusForeigner.INSIDE)
        }

    override suspend fun listSend(): Result<List<MovEquipVisitTercRoomModel>> =
        result(getClassAndMethod()) {
            movEquipVisitTercDao.listByStatusSend(StatusSend.SEND)
        }

    override suspend fun listSent(): Result<List<MovEquipVisitTercRoomModel>> =
        result(getClassAndMethod()) {
            movEquipVisitTercDao.listByStatusSend(StatusSend.SENT)
        }

    override suspend fun save(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Long> =
        result(getClassAndMethod()) {
            movEquipVisitTercDao.insert(movEquipVisitTercRoomModel)
        }

    override suspend fun setClose(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusMovEquipVisitTerc = StatusData.CLOSE
            }
        }

    override suspend fun setDestino(destino: String, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.destinoMovEquipVisitTerc = destino
                this.statusSendMovEquipVisitTerc = StatusSend.SEND
            }
        }

    override suspend fun setIdVisitTerc(idVisitTerc: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.idVisitTercMovEquipVisitTerc = idVisitTerc
                this.statusSendMovEquipVisitTerc = StatusSend.SEND
            }
        }

    override suspend fun setObserv(observ: String?, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.observMovEquipVisitTerc = observ
                this.statusSendMovEquipVisitTerc = StatusSend.SEND
            }
        }

    override suspend fun setOutside(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusMovEquipForeigVisitTerc = StatusForeigner.OUTSIDE
            }
        }

    override suspend fun setPlaca(placa: String, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.placaMovEquipVisitTerc = placa
                this.statusSendMovEquipVisitTerc = StatusSend.SEND
            }
        }

    override suspend fun setVeiculo(veiculo: String, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.veiculoMovEquipVisitTerc = veiculo
                this.statusSendMovEquipVisitTerc = StatusSend.SEND
            }
        }

    override suspend fun setSent(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusSendMovEquipVisitTerc = StatusSend.SENT
            }
        }

    override suspend fun setSend(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusSendMovEquipVisitTerc = StatusSend.SEND
            }
        }

    suspend fun update(model: MovEquipVisitTercRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            movEquipVisitTercDao.update(model)
        }
}