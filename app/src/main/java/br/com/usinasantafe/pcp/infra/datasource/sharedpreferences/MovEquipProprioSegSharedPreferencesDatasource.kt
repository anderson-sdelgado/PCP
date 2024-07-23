package br.com.usinasantafe.pcp.infra.datasource.sharedpreferences

interface MovEquipProprioSegSharedPreferencesDatasource {

    suspend fun addEquipSeg(idEquip: Long): Boolean

    suspend fun clearEquipSeg(): Boolean

    suspend fun deleteEquipSeg(pos: Int): Boolean

    suspend fun listEquipSeg(): List<Long>

}