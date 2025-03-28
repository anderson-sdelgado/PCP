package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.utils.FlowApp

interface MovEquipResidenciaRepository {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun delete(id: Int): Result<Boolean>
    suspend fun get(id: Int): Result<MovEquipResidencia>
    suspend fun getMotorista(id: Int): Result<String>
    suspend fun getObserv(id: Int): Result<String?>
    suspend fun getPlaca(id: Int): Result<String>
    suspend fun getVeiculo(id: Int): Result<String>
    suspend fun listOpen(): Result<List<MovEquipResidencia>>
    suspend fun listInside(): Result<List<MovEquipResidencia>>
    suspend fun listSend(): Result<List<MovEquipResidencia>>
    suspend fun listSent(): Result<List<MovEquipResidencia>>
    suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int>

    suspend fun send(
        list: List<MovEquipResidencia>,
        number: Long,
        token: String
    ): Result<List<MovEquipResidencia>>

    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setMotorista(
        motorista: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setObserv(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setOutside(id: Int): Result<Boolean>
    suspend fun setPlaca(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setVeiculo(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setSent(
        list: List<MovEquipResidencia>
    ): Result<Boolean>

    suspend fun start(): Result<Boolean>
    suspend fun start(movEquipResidencia: MovEquipResidencia): Result<Boolean>
}