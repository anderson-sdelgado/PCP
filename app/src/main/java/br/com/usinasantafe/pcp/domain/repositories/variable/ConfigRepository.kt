package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.lib.FlagUpdate
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.EmptyResult

interface ConfigRepository {

    suspend fun clean(): EmptyResult
    suspend fun getConfig(): Result<Config>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
    suspend fun getPassword(): Result<String>
    suspend fun getMatricVigia(): Result<Int>
    suspend fun hasConfig(): Result<Boolean>
    suspend fun saveInitial(number: Long, password: String, version: String, idServ: Int): EmptyResult
    suspend fun send(config: Config): Result<Int>
    suspend fun setFlagUpdate(flagUpdate: FlagUpdate): EmptyResult
    suspend fun setIdLocal(idLocal: Int): EmptyResult
    suspend fun setMatricVigia(matric: Int): EmptyResult
    suspend fun setStatusSend(statusSend: StatusSend): EmptyResult
}