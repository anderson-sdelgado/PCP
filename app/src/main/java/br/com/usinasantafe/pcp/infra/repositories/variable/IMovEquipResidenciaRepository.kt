package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipResidenciaRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.retrofitModelInputToEntity
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.required
import javax.inject.Inject

class IMovEquipResidenciaRepository @Inject constructor(
    private val movEquipResidenciaSharedPreferencesDatasource: MovEquipResidenciaSharedPreferencesDatasource,
    private val movEquipResidenciaRoomDatasource: MovEquipResidenciaRoomDatasource,
    private val movEquipResidenciaRetrofitDatasource: MovEquipResidenciaRetrofitDatasource
) : MovEquipResidenciaRepository {

    override suspend fun hasOpen(): Result<Boolean> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.hasOpen().getOrThrow()
        }

    override suspend fun hasSend(): Result<Boolean> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.hasSend().getOrThrow()
        }

    override suspend fun deleteById(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.deleteById(id).getOrThrow()
        }

    override suspend fun getById(id: Int): Result<MovEquipResidencia> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.get(id).getOrThrow().roomModelToEntity()
        }

    override suspend fun getMotoristaById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.get(id).getOrThrow().motoristaMovEquipResidencia
        }

    override suspend fun getObservById(id: Int): Result<String?> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.get(id).getOrThrow().observMovEquipResidencia
        }

    override suspend fun getPlacaById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.get(id).getOrThrow().placaMovEquipResidencia
        }

    override suspend fun getVeiculoById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.get(id).getOrThrow().veiculoMovEquipResidencia
        }

    override suspend fun listOpen(): Result<List<MovEquipResidencia>> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.listOpen().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listInside(): Result<List<MovEquipResidencia>> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.listInside().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listSend(): Result<List<MovEquipResidencia>> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.listSend().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun listSent(): Result<List<MovEquipResidencia>> =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.listSent().getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun save(matricVigia: Int, idLocal: Int): Result<Int> =
        call(getClassAndMethod()) {
            val sharedPreferencesModel = movEquipResidenciaSharedPreferencesDatasource.get().getOrThrow()
            val roomModel =
                sharedPreferencesModel.sharedPreferencesModelToEntity()
                    .entityToRoomModel(
                        matricVigia = matricVigia,
                        idLocal = idLocal
                    )
            val id = movEquipResidenciaRoomDatasource.save(roomModel).getOrThrow().toInt()
            if (id == 0) throw Exception("Id is 0")
            movEquipResidenciaSharedPreferencesDatasource.clean().getOrThrow()
            id
        }

    override suspend fun send(list: List<MovEquipResidencia>, number: Long, token: String): Result<List<MovEquipResidencia>> =
        call(getClassAndMethod()) {
            val retrofitModelOutputList = list.map { it.entityToRetrofitModelOutput(number) }
            val retrofitModelInputList = movEquipResidenciaRetrofitDatasource.send(retrofitModelOutputList, token).getOrThrow()
            retrofitModelInputList.map { it.retrofitModelInputToEntity() }
        }

    override suspend fun setClose(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.setClose(id).getOrThrow()
        }

    override suspend fun setMotorista(motorista: String, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setMotorista(motorista).getOrThrow()
                FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setMotorista(motorista, id).getOrThrow()
            }
        }

    override suspend fun setObserv(observ: String?, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setObserv(observ).getOrThrow()
                FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setObserv(observ, id).getOrThrow()
            }
        }

    override suspend fun setOutside(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipResidenciaRoomDatasource.setOutside(id).getOrThrow()
        }

    override suspend fun setPlaca(placa: String, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setPlaca(placa).getOrThrow()
                FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setPlaca(placa, id).getOrThrow()
            }
        }

    override suspend fun setVeiculo(veiculo: String, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setVeiculo(veiculo).getOrThrow()
                FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setVeiculo(veiculo, id).getOrThrow()
            }
        }

    override suspend fun setSent(list: List<MovEquipResidencia>): EmptyResult =
        call(getClassAndMethod()) {
            list.forEach { movEquipResidenciaRoomDatasource.setSent(it::idMovEquipResidencia.required()).getOrThrow() }
        }

    override suspend fun start(): EmptyResult =
        call(getClassAndMethod()) {
            movEquipResidenciaSharedPreferencesDatasource.save(MovEquipResidenciaSharedPreferencesModel())
        }

    override suspend fun start(movEquipResidencia: MovEquipResidencia): EmptyResult =
        call(getClassAndMethod()) {
            val sharedPreferencesModel = movEquipResidencia.sharedPreferencesModelToEntity()
            movEquipResidenciaSharedPreferencesDatasource.save(sharedPreferencesModel).getOrThrow()
        }

}