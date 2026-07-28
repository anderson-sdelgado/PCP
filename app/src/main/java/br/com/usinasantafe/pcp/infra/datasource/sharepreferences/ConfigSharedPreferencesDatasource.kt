package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pcp.lib.FlagUpdate
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.EmptyResult

interface ConfigSharedPreferencesDatasource {
    suspend fun clean(): EmptyResult
    suspend fun has(): Result<Boolean>
    suspend fun get(): Result<ConfigSharedPreferencesModel>
    suspend fun save(model: ConfigSharedPreferencesModel): EmptyResult
    suspend fun setFlagUpdate(flagUpdate: FlagUpdate): EmptyResult
    suspend fun setIdLocal(idLocal: Int): EmptyResult
    suspend fun setMatricVigia(matric: Int): EmptyResult
    suspend fun setStatusSend(statusSend: StatusSend): EmptyResult
}