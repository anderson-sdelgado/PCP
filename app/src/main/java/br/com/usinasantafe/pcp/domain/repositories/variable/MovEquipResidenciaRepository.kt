package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipResidenciaRepository {
    suspend fun hasOpen(): Result<Boolean>
    suspend fun hasSend(): Result<Boolean>
    suspend fun deleteById(id: Int): EmptyResult
    suspend fun getById(id: Int): Result<MovEquipResidencia>
    suspend fun getMotoristaById(id: Int): Result<String>
    suspend fun getObservById(id: Int): Result<String?>
    suspend fun getPlacaById(id: Int): Result<String>
    suspend fun getVeiculoById(id: Int): Result<String>
    suspend fun listOpen(): Result<List<MovEquipResidencia>>
    suspend fun listInside(): Result<List<MovEquipResidencia>>
    suspend fun listSend(): Result<List<MovEquipResidencia>>
    suspend fun listSent(): Result<List<MovEquipResidencia>>
    suspend fun save(matricVigia: Int, idLocal: Int): Result<Int>
    suspend fun send(list: List<MovEquipResidencia>, number: Long, token: String): Result<List<MovEquipResidencia>>
    suspend fun setClose(id: Int): EmptyResult
    suspend fun setMotorista(motorista: String, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setObserv(observ: String?, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setOutside(id: Int): EmptyResult
    suspend fun setPlaca(placa: String, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setVeiculo(veiculo: String, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setSent(list: List<MovEquipResidencia>): EmptyResult
    suspend fun start(): EmptyResult
    suspend fun start(movEquipResidencia: MovEquipResidencia): EmptyResult
}