package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovChaveRepository {
    suspend fun hasOpen(): Result<Boolean>
    suspend fun hasSend(): Result<Boolean>
    suspend fun getById(id: Int): Result<MovChave>
    suspend fun getMatricColabById(id: Int): Result<Int>
    suspend fun getObservById(id: Int): Result<String?>
    suspend fun listInside(): Result<List<MovChave>>
    suspend fun listOpen(): Result<List<MovChave>>
    suspend fun listSend(): Result<List<MovChave>>
    suspend fun save(matricVigia: Int, idLocal: Int, uuid: String): Result<Int>
    suspend fun send(list: List<MovChave>, number: Long, token: String): Result<List<MovChave>>
    suspend fun setClose(id: Int): EmptyResult
    suspend fun setIdChave(idChave: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setMatricColab(matricColab: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setObserv(observ: String?, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun setOutside(id: Int): EmptyResult
    suspend fun setSent(list: List<MovChave>): EmptyResult
    suspend fun start(): EmptyResult
    suspend fun start(movChave: MovChave): EmptyResult
}