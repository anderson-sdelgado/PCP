package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

interface MovEquipVisitTercPassagSharedPreferencesDatasource {
    suspend fun add(idVisitTerc: Int): Result<Boolean>
    suspend fun clean(): Result<Boolean>
    suspend fun delete(idVisitTerc: Int): Result<Boolean>
    suspend fun list(): Result<List<Int>>
}