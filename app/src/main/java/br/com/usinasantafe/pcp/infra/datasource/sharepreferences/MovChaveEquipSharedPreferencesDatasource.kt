package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovChaveEquipSharedPreferencesDatasource {
    suspend fun clean(): EmptyResult
    suspend fun get(): Result<MovChaveEquipSharedPreferencesModel>
    suspend fun setIdEquip(idEquip: Int): EmptyResult
    suspend fun setMatricColab(matricColab: Int): EmptyResult
    suspend fun setObserv(observ: String?): EmptyResult
    suspend fun save(
        model: MovChaveEquipSharedPreferencesModel
    ): EmptyResult
}