package br.com.usinasantafe.pcp.infra.datasource.sharedpreferences

interface MovEquipVisitTercPassagSharedPreferencesDatasource {

    suspend fun addPassag(idVisitTerc: Long): Boolean

    suspend fun clearPassag(): Boolean

    suspend fun deletePassag(pos: Int): Boolean

    suspend fun listPassag(): List<Long>
}