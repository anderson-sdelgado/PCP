package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipProprioPassagSharedPreferencesDatasource {
    suspend fun add(matricColab: Int): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun delete(matricColab: Int): EmptyResult
    suspend fun list(): Result<List<Int>>
}