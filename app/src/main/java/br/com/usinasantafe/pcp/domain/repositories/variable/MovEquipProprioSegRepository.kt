package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioSeg


interface MovEquipProprioSegRepository {

    suspend fun addEquipSeg(idEquip: Long): Boolean

    suspend fun addEquipSeg(idEquip: Long, idMov: Long): Boolean

    suspend fun clearEquipSeg(): Boolean

    suspend fun deleteEquipSeg(pos: Int): Boolean

    suspend fun deleteEquipSeg(pos: Int, idMov: Long): Boolean

    suspend fun deleteEquipSeg(idMov: Long): Boolean

    suspend fun listEquipSeg(): List<MovEquipProprioSeg>

    suspend fun listEquipSeg(idMov: Long): List<MovEquipProprioSeg>

    suspend fun saveEquipSeg(idMov: Long): Boolean

}