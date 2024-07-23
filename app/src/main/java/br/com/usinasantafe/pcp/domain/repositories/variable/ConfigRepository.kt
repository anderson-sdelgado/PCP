package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {

    suspend fun hasConfig(): Boolean

    suspend fun getConfig(): Config

    suspend fun saveConfig(config: Config)

    suspend fun recoverToken(config: Config): Flow<Result<Config>>

    suspend fun setStatusSendConfig(statusSend: StatusSend)

    suspend fun clearAllDataConfig()

}