package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipVisitTercRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.retrofitModelInputToEntity
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.required
import javax.inject.Inject

class IMovEquipVisitTercRepository @Inject constructor(
    private val movEquipVisitTercSharedPreferencesDatasource: MovEquipVisitTercSharedPreferencesDatasource,
    private val movEquipVisitTercRoomDatasource: MovEquipVisitTercRoomDatasource,
    private val movEquipVisitTercRetrofitDatasource: MovEquipVisitTercRetrofitDatasource,
) : MovEquipVisitTercRepository {

    override suspend fun hasOpen(): Result<Boolean> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.checkOpen().getOrThrow()
        }

    override suspend fun hasSend(): Result<Boolean> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.checkSend().getOrThrow()
        }

    override suspend fun deleteById(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.deleteById(id).getOrThrow()
        }

    override suspend fun getById(id: Int): Result<MovEquipVisitTerc> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.get(id).getOrThrow().roomModelToEntity()
        }

    override suspend fun getDestinoById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.get(id).getOrThrow()::destinoMovEquipVisitTerc.required()
        }

    override suspend fun getIdVisitTercById(id: Int): Result<Int> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.get(id).getOrThrow().idVisitTercMovEquipVisitTerc
        }

    override suspend fun getObservById(id: Int): Result<String?> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.get(id).getOrThrow().observMovEquipVisitTerc
        }

    override suspend fun getPlacaById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.get(id).getOrThrow().placaMovEquipVisitTerc
        }

    override suspend fun getTypeVisitTercByFlowAndId(flowApp: FlowApp, id: Int): Result<TypeVisitTerc> =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> {
                    movEquipVisitTercSharedPreferencesDatasource.get().getOrThrow()::tipoVisitTercMovEquipVisitTerc.required()
                }
                FlowApp.CHANGE -> {
                    movEquipVisitTercRoomDatasource.get(id).getOrThrow().tipoVisitTercMovEquipVisitTerc
                }
            }
        }

    override suspend fun getVeiculoById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.get(id).getOrThrow().veiculoMovEquipVisitTerc
        }

    override suspend fun listOpen(): Result<List<MovEquipVisitTerc>> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.listOpen().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listInside(): Result<List<MovEquipVisitTerc>> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.listInside().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listSend(): Result<List<MovEquipVisitTerc>> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.listSend().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listSent(): Result<List<MovEquipVisitTerc>> =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.listSent().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun save(matricVigia: Int, idLocal: Int): Result<Int> =
        call(getClassAndMethod()) {
            val sharedPreferencesModel = movEquipVisitTercSharedPreferencesDatasource.get().getOrThrow()
            val roomModel = sharedPreferencesModel
                .sharedPreferencesModelToEntity()
                .entityToRoomModel(matricVigia, idLocal)
            val id = movEquipVisitTercRoomDatasource.save(roomModel).getOrThrow().toInt()
            if (id == 0) throw Exception("Id is 0")
            movEquipVisitTercSharedPreferencesDatasource.clear().getOrThrow()
            id
        }

    override suspend fun send(list: List<MovEquipVisitTerc>, number: Long, token: String): Result<List<MovEquipVisitTerc>> =
        call(getClassAndMethod()) {
            val retrofitModelOutputList = list.map { it.entityToRetrofitModelOutput(number) }
            val retrofitModelInputList = movEquipVisitTercRetrofitDatasource.send(retrofitModelOutputList, token).getOrThrow()
            retrofitModelInputList.map { it.retrofitModelInputToEntity() }
        }

    override suspend fun setClose(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.setClose(id).getOrThrow()
        }

    override suspend fun setDestino(destino: String, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setDestino(destino).getOrThrow()
                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setDestino(destino, id).getOrThrow()
            }
        }

    override suspend fun setIdVisitTerc(idVisitTerc: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc(idVisitTerc).getOrThrow()
                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setIdVisitTerc(idVisitTerc, id).getOrThrow()
            }
        }

    override suspend fun setObserv(observ: String?, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setObserv(observ).getOrThrow()
                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setObserv(observ, id).getOrThrow()
            }
        }

    override suspend fun setOutside(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.setOutside(id).getOrThrow()
        }

    override suspend fun setPlaca(placa: String, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setPlaca(placa).getOrThrow()
                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setPlaca(placa, id).getOrThrow()
            }
        }

    override suspend fun setTipoVisitTerc(typeVisitTerc: TypeVisitTerc): EmptyResult =
        call(getClassAndMethod()) {
            movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(typeVisitTerc).getOrThrow()
        }

    override suspend fun setVeiculo(veiculo: String, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setVeiculo(veiculo).getOrThrow()
                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setVeiculo(veiculo, id).getOrThrow()
            }
        }

    override suspend fun setSent(list: List<MovEquipVisitTerc>): EmptyResult =
        call(getClassAndMethod()) {
            list.forEach { movEquipVisitTercRoomDatasource.setSent(it::idMovEquipVisitTerc.required()).getOrThrow() }
        }

    override suspend fun setSend(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipVisitTercRoomDatasource.setSend(id).getOrThrow()
        }

    override suspend fun start(): EmptyResult =
        call(getClassAndMethod()) {
            movEquipVisitTercSharedPreferencesDatasource.save(MovEquipVisitTercSharedPreferencesModel()).getOrThrow()
        }

    override suspend fun start(movEquipVisitTerc: MovEquipVisitTerc): EmptyResult =
        call(getClassAndMethod()) {
            val sharedPreferencesModel = movEquipVisitTerc.sharedPreferencesModelToEntity()
            movEquipVisitTercSharedPreferencesDatasource.save(sharedPreferencesModel).getOrThrow()
        }

}