package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.domain.entities.variable.Config

interface ConfigSharedPreferencesDatasource {
    suspend fun clean(): Result<Boolean>
    suspend fun has(): Result<Boolean>
    suspend fun get(): Result<Config>
    suspend fun save(config: Config): Result<Boolean>
}