package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveEquipDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveEquipRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovChaveEquipRoomDatasource @Inject constructor(
    private val movChaveEquipDao: MovChaveEquipDao
): MovChaveEquipRoomDatasource {

    suspend fun updateModel(id: Int, block: MovChaveEquipRoomModel.() -> Unit) {
        val model = get(id).getOrThrow()
        model.block()
        update(model).getOrThrow()
    }

    override suspend fun checkOpen(): Result<Boolean> =
        result(getClassAndMethod()) {
            movChaveEquipDao.hasByStatusData(StatusData.OPEN)
        }

    override suspend fun checkSend(): Result<Boolean> =
        result(getClassAndMethod()) {
            movChaveEquipDao.hasByStatusSend(StatusSend.SEND)
        }

    override suspend fun get(id: Int): Result<MovChaveEquipRoomModel> =
        result(getClassAndMethod()) {
            movChaveEquipDao.getById(id)
        }

    override suspend fun listInside(): Result<List<MovChaveEquipRoomModel>> =
        result(getClassAndMethod()) {
            movChaveEquipDao.listByStatusForeigner(StatusForeigner.INSIDE)
        }

    override suspend fun listOpen(): Result<List<MovChaveEquipRoomModel>> =
        result(getClassAndMethod()) {
            movChaveEquipDao.listByStatusData(StatusData.OPEN)
        }

    override suspend fun listSend(): Result<List<MovChaveEquipRoomModel>> =
        result(getClassAndMethod()) {
            movChaveEquipDao.listByStatusSend(StatusSend.SEND)
        }

    override suspend fun save(movChaveEquipRoomModel: MovChaveEquipRoomModel): Result<Long> =
        result(getClassAndMethod()) {
            movChaveEquipDao.insert(movChaveEquipRoomModel)
        }

    override suspend fun setClose(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusMovChaveEquip = StatusData.CLOSE
            }
        }

    override suspend fun setIdEquip(idEquip: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.idEquipMovChaveEquip = idEquip
                this.statusSendMovChaveEquip = StatusSend.SEND
            }
        }

    override suspend fun setObserv(observ: String?, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.observMovChaveEquip = observ
                this.statusSendMovChaveEquip = StatusSend.SEND
            }
        }

    override suspend fun setMatricColab(matric: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.matricColabMovChaveEquip = matric
                this.statusSendMovChaveEquip = StatusSend.SEND
            }
        }

    override suspend fun setSent(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusSendMovChaveEquip = StatusSend.SENT
            }
        }

    override suspend fun setOutside(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusForeignerMovChaveEquip = StatusForeigner.OUTSIDE
            }
        }

    suspend fun update(model: MovChaveEquipRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            movChaveEquipDao.update(model)
        }

}