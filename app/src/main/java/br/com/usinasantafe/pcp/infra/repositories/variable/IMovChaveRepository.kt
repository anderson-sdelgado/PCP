package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.retrofitModelInputToEntity
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.required
import javax.inject.Inject

class IMovChaveRepository @Inject constructor(
    private val movChaveRoomDatasource: MovChaveRoomDatasource,
    private val movChaveSharedPreferencesDatasource: MovChaveSharedPreferencesDatasource,
    private val movChaveRetrofitDatasource: MovChaveRetrofitDatasource
): MovChaveRepository {

    override suspend fun hasOpen(): Result<Boolean> =
        call(getClassAndMethod()) {
            movChaveRoomDatasource.checkOpen().getOrThrow()
        }

    override suspend fun hasSend(): Result<Boolean> =
        call(getClassAndMethod()) {
            movChaveRoomDatasource.checkSend().getOrThrow()
        }

    override suspend fun getById(id: Int): Result<MovChave> =
        call(getClassAndMethod()) {
            movChaveRoomDatasource.get(id).getOrThrow().roomModelToEntity()
        }

    override suspend fun getMatricColabById(id: Int): Result<Int> =
        call(getClassAndMethod()) {
            movChaveRoomDatasource.get(id).getOrThrow().matricColabMovChave
        }

    override suspend fun getObservById(id: Int): Result<String?> =
        call(getClassAndMethod()) {
            movChaveRoomDatasource.get(id).getOrThrow().observMovChave
        }

    override suspend fun listInside(): Result<List<MovChave>> =
        call(getClassAndMethod()) {
            movChaveRoomDatasource.listInside().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listOpen(): Result<List<MovChave>> =
        call(getClassAndMethod()) {
            movChaveRoomDatasource.listOpen().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listSend(): Result<List<MovChave>> =
        call(getClassAndMethod()) {
            movChaveRoomDatasource.listSend().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun save(matricVigia: Int, idLocal: Int, uuid: String): Result<Int> =
        call(getClassAndMethod()) {
            val sharedPreferencesModel = movChaveSharedPreferencesDatasource.get().getOrThrow()
            val roomModel = sharedPreferencesModel
                .sharedPreferencesModelToEntity()
                .entityToRoomModel(
                    matricVigia = matricVigia,
                    idLocal = idLocal,
                    uuid = uuid
                )
            val id = movChaveRoomDatasource.save(roomModel).getOrThrow().toInt()
            if (id == 0) throw Exception("Id is 0")
            movChaveSharedPreferencesDatasource.clean().getOrThrow()
            id
        }

    override suspend fun send(list: List<MovChave>, number: Long, token: String): Result<List<MovChave>> =
        call(getClassAndMethod()) {
            val retrofitModelOutputList = list.map { it.entityToRetrofitModelOutput(number) }
            val retrofitModelInputList = movChaveRetrofitDatasource.send(retrofitModelOutputList, token).getOrThrow()
            retrofitModelInputList.map { it.retrofitModelInputToEntity() }
        }

    override suspend fun setClose(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movChaveRoomDatasource.setClose(id)
        }

    override suspend fun setIdChave(idChave: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movChaveSharedPreferencesDatasource.setIdChave(idChave).getOrThrow()
                FlowApp.CHANGE -> movChaveRoomDatasource.setIdChave(idChave, id).getOrThrow()
            }
        }

    override suspend fun setMatricColab(matricColab: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movChaveSharedPreferencesDatasource.setMatricColab(matricColab).getOrThrow()
                FlowApp.CHANGE -> movChaveRoomDatasource.setMatricColab(matricColab, id).getOrThrow()
            }
        }

    override suspend fun setObserv(observ: String?, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movChaveSharedPreferencesDatasource.setObserv(observ).getOrThrow()
                FlowApp.CHANGE -> movChaveRoomDatasource.setObserv(observ, id).getOrThrow()
            }
        }

    override suspend fun setOutside(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movChaveRoomDatasource.setOutside(id).getOrThrow()
        }

    override suspend fun setSent(list: List<MovChave>): EmptyResult =
        call(getClassAndMethod()) {
            list.forEach { movChaveRoomDatasource.setSent(it::idMovChave.required()) }
        }

    override suspend fun start(): EmptyResult =
        call(getClassAndMethod()) {
            movChaveSharedPreferencesDatasource.save(MovChaveSharedPreferencesModel()).getOrThrow()
        }

    override suspend fun start(movChave: MovChave): EmptyResult =
        call(getClassAndMethod()) {
            val sharedPreferenceModel = movChave.sharedPreferencesModelToEntity()
            movChaveSharedPreferencesDatasource.save(sharedPreferenceModel).getOrThrow()
        }

}