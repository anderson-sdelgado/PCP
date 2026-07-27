package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovChaveRoomDatasource @Inject constructor(
    private val movChaveDao: MovChaveDao
): MovChaveRoomDatasource {

    suspend fun updateModel(id: Int, block: MovChaveRoomModel.() -> Unit) {
        val model = get(id).getOrThrow()
        model.block()
        update(model).getOrThrow()
    }

    override suspend fun checkOpen(): Result<Boolean> =
        result(getClassAndMethod()) {
            movChaveDao.hasByStatusData(StatusData.OPEN)
        }

    override suspend fun checkSend(): Result<Boolean> =
        result(getClassAndMethod()) {
            movChaveDao.hasByStatusSend(StatusSend.SEND)
        }

    override suspend fun get(id: Int): Result<MovChaveRoomModel> =
        result(getClassAndMethod()) {
            movChaveDao.getById(id)
        }

    override suspend fun listInside(): Result<List<MovChaveRoomModel>> =
        result(getClassAndMethod()) {
            movChaveDao.listByStatusForeigner(StatusForeigner.INSIDE)
        }

    override suspend fun listOpen(): Result<List<MovChaveRoomModel>> =
        result(getClassAndMethod()) {
            movChaveDao.listByStatusData(StatusData.OPEN)
        }

    override suspend fun listSend(): Result<List<MovChaveRoomModel>> =
        result(getClassAndMethod()) {
            movChaveDao.listByStatusSend(StatusSend.SEND)
        }

    override suspend fun save(movChaveRoomModel: MovChaveRoomModel): Result<Long> =
        result(getClassAndMethod()) {
            movChaveDao.insert(movChaveRoomModel)
        }

    override suspend fun setClose(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusMovChave = StatusData.CLOSE
            }
        }

    override suspend fun setIdChave(idChave: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.idChaveMovChave = idChave
                this.statusSendMovChave = StatusSend.SEND
            }
        }

    override suspend fun setObserv(observ: String?, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.observMovChave = observ
                this.statusSendMovChave = StatusSend.SEND
            }
        }

    override suspend fun setMatricColab(matric: Int, id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.matricColabMovChave = matric
                this.statusSendMovChave = StatusSend.SEND
            }
        }

    override suspend fun setSent(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusSendMovChave = StatusSend.SENT
            }
        }

    override suspend fun setOutside(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel(id) {
                this.statusForeignerMovChave = StatusForeigner.OUTSIDE
            }
        }

    suspend fun update(model: MovChaveRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            movChaveDao.update(model)
        }

}