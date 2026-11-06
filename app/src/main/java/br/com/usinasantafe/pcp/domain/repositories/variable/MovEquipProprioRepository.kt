package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipProprioRepository {
    suspend fun hasOpen(): Result<Boolean>
    suspend fun hasSend(): Result<Boolean>
    suspend fun deleteById(id: Int): EmptyResult
    suspend fun getById(id: Int): Result<MovEquipProprio>
    suspend fun getDestinoById(id: Int): Result<String>
    suspend fun getIdEquipById(id: Int): Result<Int>
    suspend fun getMatricColabById(id: Int): Result<Int>
    suspend fun getNotaFiscalById(id: Int): Result<Int?>
    suspend fun getObservById(id: Int): Result<String?>
    suspend fun getTipoMovById(): Result<TypeMovEquip>
    suspend fun listOpen(): Result<List<MovEquipProprio>>
    suspend fun listSend(): Result<List<MovEquipProprio>>
    suspend fun listSent(): Result<List<MovEquipProprio>>
    suspend fun save(matricVigia: Int, idLocal: Int): Result<Int>
    suspend fun send(list: List<MovEquipProprio>, number: Long, token: String): Result<List<MovEquipProprio>>
    suspend fun setClose(id: Int): EmptyResult
    suspend fun setDestino(destino: String, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setIdEquip(idEquip: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setNotaFiscal(notaFiscal: Int?, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setMatricColab(matricColab: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setObserv(observ: String?, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setSent(list: List<MovEquipProprio>): EmptyResult
    suspend fun setSend(id: Int): EmptyResult
    suspend fun start(typeMov: TypeMovEquip): EmptyResult
}