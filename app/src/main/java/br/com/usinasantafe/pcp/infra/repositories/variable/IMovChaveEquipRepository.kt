package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveEquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveEquipRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveEquipSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.retrofitModelInputToEntity
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.required
import javax.inject.Inject

class IMovChaveEquipRepository @Inject constructor(
    private val movChaveEquipRoomDatasource: MovChaveEquipRoomDatasource,
    private val movChaveEquipSharedPreferencesDatasource: MovChaveEquipSharedPreferencesDatasource,
    private val movChaveEquipRetrofitDatasource: MovChaveEquipRetrofitDatasource
): MovChaveEquipRepository {

    override suspend fun hasOpen(): Result<Boolean> =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.checkOpen().getOrThrow()
        }

    override suspend fun hasSend(): Result<Boolean> =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.checkSend().getOrThrow()
        }

    override suspend fun getById(id: Int): Result<MovChaveEquip> =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.getById(id).getOrThrow().roomModelToEntity()
        }

    override suspend fun getMatricColabById(id: Int): Result<Int> =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.getById(id).getOrThrow().matricColabMovChaveEquip
        }

    override suspend fun getIdEquipById(id: Int): Result<Int> =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.getById(id).getOrThrow().idEquipMovChaveEquip
        }

    override suspend fun getObservById(id: Int): Result<String?> =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.getById(id).getOrThrow().observMovChaveEquip
        }

    override suspend fun listInside(): Result<List<MovChaveEquip>> =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.listInside().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listOpen(): Result<List<MovChaveEquip>> =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.listOpen().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listSend(): Result<List<MovChaveEquip>> =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.listSend().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun save(matricVigia: Int, idLocal: Int, uuid: String): Result<Int> =
        call(getClassAndMethod()) {
            val sharedPreferencesModel = movChaveEquipSharedPreferencesDatasource.get().getOrThrow()
            val roomModel = sharedPreferencesModel
                .sharedPreferencesModelToEntity()
                .entityToRoomModel(
                    matricVigia = matricVigia,
                    idLocal = idLocal,
                    uuid = uuid
                )
            val id = movChaveEquipRoomDatasource.save(roomModel).getOrThrow().toInt()
            if (id == 0) throw Exception("Id is 0")
            movChaveEquipSharedPreferencesDatasource.clean().getOrThrow()
            id
        }

    override suspend fun send(list: List<MovChaveEquip>, number: Long, token: String): Result<List<MovChaveEquip>> =
        call(getClassAndMethod()) {
            val retrofitModelOutputList = list.map { it.entityToRetrofitModelOutput(number) }
            val retrofitModelInputList = movChaveEquipRetrofitDatasource.send(retrofitModelOutputList, token).getOrThrow()
            retrofitModelInputList.map { it.retrofitModelInputToEntity() }
        }

    override suspend fun setClose(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.setClose(id).getOrThrow()
        }

    override suspend fun setIdEquip(idEquip: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movChaveEquipSharedPreferencesDatasource.setIdEquip(idEquip).getOrThrow()
                FlowApp.CHANGE -> movChaveEquipRoomDatasource.setIdEquip(idEquip, id).getOrThrow()
            }
        }

    override suspend fun setMatricColab(matricColab: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movChaveEquipSharedPreferencesDatasource.setMatricColab(matricColab).getOrThrow()
                FlowApp.CHANGE -> movChaveEquipRoomDatasource.setMatricColab(matricColab, id).getOrThrow()
            }
        }

    override suspend fun setObserv(observ: String?, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movChaveEquipSharedPreferencesDatasource.setObserv(observ).getOrThrow()
                FlowApp.CHANGE -> movChaveEquipRoomDatasource.setObserv(observ, id).getOrThrow()
            }
        }

    override suspend fun setOutside(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movChaveEquipRoomDatasource.setOutside(id).getOrThrow()
        }

    override suspend fun setSent(list: List<MovChaveEquip>): EmptyResult =
        call(getClassAndMethod()) {
            list.forEach { movChaveEquipRoomDatasource.setSent(it::idMovChaveEquip.required()) }
        }

    override suspend fun start(): EmptyResult =
        call(getClassAndMethod()) {
            movChaveEquipSharedPreferencesDatasource.save(MovChaveEquipSharedPreferencesModel()).getOrThrow()
        }

    override suspend fun start(movChaveEquip: MovChaveEquip): EmptyResult =
        call(getClassAndMethod()) {
            val sharedPreferenceModel = movChaveEquip.entityToSharedPreferencesModel()
            movChaveEquipSharedPreferencesDatasource.save(sharedPreferenceModel).getOrThrow()
        }
}