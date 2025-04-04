package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.utils.FlagUpdate
import br.com.usinasantafe.pcp.utils.StatusSend

interface ConfigRepository {

    suspend fun clean(): Result<Boolean>
    suspend fun getConfig(): Result<Config>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
    suspend fun getPassword(): Result<String>
    suspend fun getMatricVigia(): Result<Int>
    suspend fun hasConfig(): Result<Boolean>
    suspend fun saveInitial(
        number: Long,
        password: String,
        version: String,
        idBD: Int
    ): Result<Boolean>

    suspend fun send(config: Config): Result<Int>
    suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean>
    suspend fun setIdLocal(idLocal: Int): Result<Boolean>
    suspend fun setMatricVigia(matric: Int): Result<Boolean>
    suspend fun setStatusSend(statusSend: StatusSend): Result<Boolean>
}