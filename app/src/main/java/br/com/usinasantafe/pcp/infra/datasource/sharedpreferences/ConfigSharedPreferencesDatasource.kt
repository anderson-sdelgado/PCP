package br.com.usinasantafe.pcp.infra.datasource.sharedpreferences

import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.domain.entities.variable.Config

interface ConfigSharedPreferencesDatasource {

    suspend fun hasConfig(): Boolean

    suspend fun getConfig(): Config

    suspend fun saveConfig(config: Config)

    suspend fun setStatusSend(statusSend: StatusSend)

    suspend fun clearAllDataConfig()

}