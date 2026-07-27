package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface ConfigSharedPreferencesDatasource {
    suspend fun clean(): EmptyResult
    suspend fun has(): Result<Boolean>
    suspend fun get(): Result<ConfigSharedPreferencesModel>
    suspend fun save(model: ConfigSharedPreferencesModel): EmptyResult
}