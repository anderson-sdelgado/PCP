package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel

interface MovChaveEquipSharedPreferencesDatasource {
    suspend fun clean(): Result<Boolean>
    suspend fun get(): Result<MovChaveEquipSharedPreferencesModel>
    suspend fun setIdEquip(idEquip: Int): Result<Boolean>
    suspend fun setMatricColab(matricColab: Int): Result<Boolean>
    suspend fun setObserv(observ: String?): Result<Boolean>
    suspend fun start(
        movChaveSharedPreferencesModel: MovChaveEquipSharedPreferencesModel =
            MovChaveEquipSharedPreferencesModel()
    ): Result<Boolean>
}