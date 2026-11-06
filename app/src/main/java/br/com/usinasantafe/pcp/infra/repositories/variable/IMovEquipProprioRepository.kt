package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipProprioRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.retrofitModelInputToEntity
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.required
import javax.inject.Inject

class IMovEquipProprioRepository @Inject constructor(
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource,
    private val movEquipProprioRoomDatasource: MovEquipProprioRoomDatasource,
    private val movEquipProprioRetrofitDatasource: MovEquipProprioRetrofitDatasource,
) : MovEquipProprioRepository {

    override suspend fun hasOpen(): Result<Boolean> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.hasOpen().getOrThrow()
        }

    override suspend fun hasSend(): Result<Boolean> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.hasSend().getOrThrow()
        }

    override suspend fun deleteById(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.deleteById(id).getOrThrow()
        }


    override suspend fun getById(id: Int): Result<MovEquipProprio> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.getById(id).getOrThrow().roomModelToEntity()
        }

    override suspend fun getDestinoById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.getById(id).getOrThrow().destinoMovEquipProprio
        }

    override suspend fun getTipoMovById(): Result<TypeMovEquip> =
        call(getClassAndMethod()) {
            movEquipProprioSharedPreferencesDatasource.get().getOrThrow()::tipoMovEquipProprio.required()
        }

    override suspend fun getIdEquipById(id: Int): Result<Int> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.getById(id).getOrThrow().idEquipMovEquipProprio
        }

    override suspend fun getObservById(id: Int): Result<String?> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.getById(id).getOrThrow().observMovEquipProprio
        }

    override suspend fun getMatricColabById(id: Int): Result<Int> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.getById(id).getOrThrow().matricColabMovEquipProprio
        }

    override suspend fun getNotaFiscalById(id: Int): Result<Int?> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.getById(id).getOrThrow().notaFiscalMovEquipProprio
        }

    override suspend fun listOpen(): Result<List<MovEquipProprio>> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.listOpen().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listSend(): Result<List<MovEquipProprio>> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.listSend().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listSent(): Result<List<MovEquipProprio>> =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.listSent().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun save(matricVigia: Int, idLocal: Int): Result<Int> =
        call(getClassAndMethod()) {
            val sharedPreferencesModel =
                movEquipProprioSharedPreferencesDatasource.get().getOrThrow()
            val roomModel = sharedPreferencesModel
                .sharedPreferencesModelToEntity()
                .entityToRoomModel(matricVigia, idLocal)
            val id = movEquipProprioRoomDatasource.save(roomModel).getOrThrow().toInt()
            if (id == 0) throw Exception("Id is 0")
            movEquipProprioSharedPreferencesDatasource.clean().getOrThrow()
            id
        }

    override suspend fun send(list: List<MovEquipProprio>, number: Long, token: String): Result<List<MovEquipProprio>> =
        call(getClassAndMethod()) {
            val retrofitModelOutputList = list.map { it.entityToRetrofitModelOutput(number) }
            val retrofitModelInputList = movEquipProprioRetrofitDatasource.send(retrofitModelOutputList, token).getOrThrow()
            retrofitModelInputList.map { it.retrofitModelInputToEntity() }
        }

    override suspend fun setClose(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.setClose(id).getOrThrow()
        }

    override suspend fun setDestino(destino: String, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setDestino(destino).getOrThrow()
                FlowApp.CHANGE -> movEquipProprioRoomDatasource.setDestino(destino, id).getOrThrow()
            }
        }

    override suspend fun setIdEquip(idEquip: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setIdEquip(idEquip).getOrThrow()
                FlowApp.CHANGE -> movEquipProprioRoomDatasource.setIdEquip(idEquip, id).getOrThrow()
            }
        }

    override suspend fun setMatricColab(matricColab: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setMatricColab(matricColab).getOrThrow()
                FlowApp.CHANGE -> movEquipProprioRoomDatasource.setMatricColab(matricColab, id).getOrThrow()
            }
        }

    override suspend fun setNotaFiscal(notaFiscal: Int?, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setNotaFiscal(notaFiscal).getOrThrow()
                FlowApp.CHANGE -> movEquipProprioRoomDatasource.setNotaFiscal(notaFiscal, id).getOrThrow()
            }
        }

    override suspend fun setObserv(observ: String?, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setObserv(observ).getOrThrow()
                FlowApp.CHANGE -> movEquipProprioRoomDatasource.setObserv(observ, id).getOrThrow()
            }
        }

    override suspend fun setSent(list: List<MovEquipProprio>): EmptyResult =
        call(getClassAndMethod()) {
            list.forEach {  movEquipProprioRoomDatasource.setSent(it::idMovEquipProprio.required()).getOrThrow() }
        }

    override suspend fun setSend(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipProprioRoomDatasource.setSend(id).getOrThrow()
        }

    override suspend fun start(typeMov: TypeMovEquip): EmptyResult =
        call(getClassAndMethod()) {
            movEquipProprioSharedPreferencesDatasource.start(typeMov).getOrThrow()
        }

}