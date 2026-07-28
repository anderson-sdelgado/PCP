package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovEquipResidenciaRoomDatasource @Inject constructor(
    private val movEquipResidenciaDao: MovEquipResidenciaDao
): MovEquipResidenciaRoomDatasource {

    suspend fun updateModel(id: Int, block: MovEquipResidenciaRoomModel.() -> Unit) {
        val model = get(id).getOrThrow()
        model.block()
        update(model).getOrThrow()
    }

    override suspend fun checkOpen(): Result<Boolean> =
        result(getClassAndMethod()) {
            movEquipResidenciaDao.hasByStatusData(StatusData.OPEN)
        }

    override suspend fun checkSend(): Result<Boolean> =
        result(getClassAndMethod()) {
            movEquipResidenciaDao.hasByStatusSend(StatusSend.SEND)
        }

    override suspend fun delete(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            movEquipResidenciaDao.delete(movEquipResidenciaRoomModel)
        }

    override suspend fun get(id: Int): Result<MovEquipResidenciaRoomModel> =
        result(getClassAndMethod()) {
            movEquipResidenciaDao.get(id)
        }

    override suspend fun listOpen(): Result<List<MovEquipResidenciaRoomModel>> =
        result(getClassAndMethod()) {
            movEquipResidenciaDao.listByStatusData(StatusData.OPEN)
        }

    override suspend fun listInside(): Result<List<MovEquipResidenciaRoomModel>> =
        result(getClassAndMethod()) {
            movEquipResidenciaDao.listByStatusForeigner(StatusForeigner.INSIDE)
        }

    override suspend fun listSend(): Result<List<MovEquipResidenciaRoomModel>> =
        result(getClassAndMethod()) {
            movEquipResidenciaDao.listByStatusSend(StatusSend.SEND)
        }

    override suspend fun listSent(): Result<List<MovEquipResidenciaRoomModel>> =
        result(getClassAndMethod()) {
            movEquipResidenciaDao.listByStatusSend(StatusSend.SENT)
        }

    override suspend fun save(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Long> =
        result(getClassAndMethod()) {
            movEquipResidenciaDao.insert(movEquipResidenciaRoomModel)
        }

    override suspend fun setClose(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusMovEquipResidencia = StatusData.CLOSE
            }
        }

    override suspend fun setMotorista(motorista: String, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.motoristaMovEquipResidencia = motorista
                this.statusSendMovEquipResidencia= StatusSend.SEND
            }
        }

    override suspend fun setObserv(observ: String?, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.observMovEquipResidencia = observ
                this.statusSendMovEquipResidencia= StatusSend.SEND
            }
        }

    override suspend fun setOutside(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusMovEquipForeignerResidencia = StatusForeigner.OUTSIDE
            }
        }

    override suspend fun setPlaca(placa: String, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.placaMovEquipResidencia = placa
                this.statusSendMovEquipResidencia= StatusSend.SEND
            }
        }

    override suspend fun setVeiculo(veiculo: String, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.veiculoMovEquipResidencia = veiculo
                this.statusSendMovEquipResidencia= StatusSend.SEND
            }
        }

    override suspend fun setSent(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusSendMovEquipResidencia= StatusSend.SENT
            }
        }

    suspend fun update(model: MovEquipResidenciaRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            movEquipResidenciaDao.update(model)
        }
}