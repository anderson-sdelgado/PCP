package br.com.usinasantafe.pcp.infra.datasource.sharedpreferences

interface MovEquipProprioPassagSharedPreferencesDatasource {

    suspend fun addPassag(nroMatric: Long): Boolean

    suspend fun clearPassag(): Boolean

    suspend fun deletePassag(pos: Int): Boolean

    suspend fun listPassag(): List<Long>
}