package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipProprioEquipSegSharedPreferencesDatasource {
    suspend fun add(idEquip: Int): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun delete(idEquip: Int): EmptyResult
    suspend fun list(): Result<List<Int>>
}