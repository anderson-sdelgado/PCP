package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovChaveSharedPreferencesDatasource {
    suspend fun clean(): EmptyResult
    suspend fun get(): Result<MovChaveSharedPreferencesModel>
    suspend fun setIdChave(idChave: Int): EmptyResult
    suspend fun setMatricColab(matricColab: Int): EmptyResult
    suspend fun setObserv(observ: String?): EmptyResult
    suspend fun save(model: MovChaveSharedPreferencesModel): EmptyResult
}