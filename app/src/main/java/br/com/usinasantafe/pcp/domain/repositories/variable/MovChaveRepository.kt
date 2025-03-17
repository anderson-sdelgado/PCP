package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.utils.FlowApp

interface MovChaveRepository {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun get(id: Int): Result<MovChave>
    suspend fun getMatricColab(id: Int): Result<Int>
    suspend fun getObserv(id: Int): Result<String?>
    suspend fun listInside(): Result<List<MovChave>>
    suspend fun listOpen(): Result<List<MovChave>>
    suspend fun listSend(): Result<List<MovChave>>
    suspend fun save(
        matricVigia: Int,
        idLocal: Int,
        uuid: String
    ): Result<Int>
    suspend fun send(
        list: List<MovChave>,
        number: Long,
        token: String
    ): Result<List<MovChave>>
    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setIdChave(
        idChave: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun setMatricColab(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun setObserv(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun setOutside(
        id: Int
    ): Result<Boolean>
    suspend fun setSent(
        list: List<MovChave>
    ): Result<Boolean>
    suspend fun start(): Result<Boolean>
    suspend fun start(movChave: MovChave): Result<Boolean>
}