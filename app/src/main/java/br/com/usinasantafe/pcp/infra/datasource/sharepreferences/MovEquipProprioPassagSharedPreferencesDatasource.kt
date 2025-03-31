package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

interface MovEquipProprioPassagSharedPreferencesDatasource {
    suspend fun add(matricColab: Int): Result<Boolean>
    suspend fun clean(): Result<Boolean>
    suspend fun delete(matricColab: Int): Result<Boolean>
    suspend fun list(): Result<List<Int>>
}