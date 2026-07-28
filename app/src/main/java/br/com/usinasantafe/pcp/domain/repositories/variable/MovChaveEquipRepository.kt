package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovChaveEquipRepository {
    suspend fun hasOpen(): Result<Boolean>
    suspend fun hasSend(): Result<Boolean>
    suspend fun getById(id: Int): Result<MovChaveEquip>
    suspend fun getMatricColabById(id: Int): Result<Int>
    suspend fun getIdEquipById(id: Int): Result<Int>
    suspend fun getObservById(id: Int): Result<String?>
    suspend fun listInside(): Result<List<MovChaveEquip>>
    suspend fun listOpen(): Result<List<MovChaveEquip>>
    suspend fun listSend(): Result<List<MovChaveEquip>>
    suspend fun save(matricVigia: Int, idLocal: Int, uuid: String): Result<Int>
    suspend fun send(list: List<MovChaveEquip>, number: Long, token: String): Result<List<MovChaveEquip>>
    suspend fun setClose(id: Int): EmptyResult
    suspend fun setIdEquip(idEquip: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setMatricColab(matricColab: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setObserv(observ: String?, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setOutside(id: Int): EmptyResult
    suspend fun setSent(list: List<MovChaveEquip>): EmptyResult
    suspend fun start(): EmptyResult
    suspend fun start(movChaveEquip: MovChaveEquip): EmptyResult
}