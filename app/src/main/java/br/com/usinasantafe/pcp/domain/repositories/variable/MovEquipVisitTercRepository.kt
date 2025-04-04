package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc

interface MovEquipVisitTercRepository {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun delete(id: Int): Result<Boolean>
    suspend fun get(id: Int): Result<MovEquipVisitTerc>
    suspend fun getDestino(id: Int): Result<String>
    suspend fun getIdVisitTerc(id: Int): Result<Int>
    suspend fun getObserv(id: Int): Result<String?>
    suspend fun getPlaca(id: Int): Result<String>
    suspend fun getTypeVisitTerc(
        flowApp: FlowApp,
        id: Int
    ): Result<TypeVisitTerc>

    suspend fun getVeiculo(id: Int): Result<String>
    suspend fun listOpen(): Result<List<MovEquipVisitTerc>>
    suspend fun listInside(): Result<List<MovEquipVisitTerc>>
    suspend fun listSend(): Result<List<MovEquipVisitTerc>>
    suspend fun listSent(): Result<List<MovEquipVisitTerc>>
    suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int>

    suspend fun send(
        list: List<MovEquipVisitTerc>,
        number: Long,
        token: String
    ): Result<List<MovEquipVisitTerc>>

    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setDestino(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setIdVisitTerc(
        idVisitTerc: Int,
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

    suspend fun setTipoVisitTerc(
        typeVisitTerc: TypeVisitTerc
    ): Result<Boolean>

    suspend fun setVeiculo(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setSent(
        list: List<MovEquipVisitTerc>
    ): Result<Boolean>

    suspend fun setSend(
        id: Int
    ): Result<Boolean>

    suspend fun start(): Result<Boolean>
    suspend fun start(movEquipVisitTerc: MovEquipVisitTerc): Result<Boolean>
}