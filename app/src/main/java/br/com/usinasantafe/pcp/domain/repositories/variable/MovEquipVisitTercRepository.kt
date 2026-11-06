package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipVisitTercRepository {
    suspend fun hasOpen(): Result<Boolean>
    suspend fun hasSend(): Result<Boolean>
    suspend fun deleteById(id: Int): EmptyResult
    suspend fun getById(id: Int): Result<MovEquipVisitTerc>
    suspend fun getDestinoById(id: Int): Result<String>
    suspend fun getIdVisitTercById(id: Int): Result<Int>
    suspend fun getObservById(id: Int): Result<String?>
    suspend fun getPlacaById(id: Int): Result<String>
    suspend fun getTypeVisitTercByFlowAndId(flowApp: FlowApp, id: Int): Result<TypeVisitTerc>
    suspend fun getVeiculoById(id: Int): Result<String>
    suspend fun listOpen(): Result<List<MovEquipVisitTerc>>
    suspend fun listInside(): Result<List<MovEquipVisitTerc>>
    suspend fun listSend(): Result<List<MovEquipVisitTerc>>
    suspend fun listSent(): Result<List<MovEquipVisitTerc>>
    suspend fun save(matricVigia: Int, idLocal: Int): Result<Int>
    suspend fun send(list: List<MovEquipVisitTerc>, number: Long, token: String): Result<List<MovEquipVisitTerc>>
    suspend fun setClose(id: Int): EmptyResult
    suspend fun setDestino(destino: String, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setIdVisitTerc(idVisitTerc: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setObserv(observ: String?, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setOutside(id: Int): EmptyResult
    suspend fun setPlaca(placa: String, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setTipoVisitTerc(typeVisitTerc: TypeVisitTerc): EmptyResult
    suspend fun setVeiculo(veiculo: String, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setSent(list: List<MovEquipVisitTerc>): EmptyResult
    suspend fun setSend(id: Int): EmptyResult
    suspend fun start(): EmptyResult
    suspend fun start(movEquipVisitTerc: MovEquipVisitTerc): EmptyResult
}