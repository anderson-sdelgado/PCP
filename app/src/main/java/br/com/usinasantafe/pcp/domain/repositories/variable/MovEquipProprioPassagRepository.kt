package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag

interface MovEquipProprioPassagRepository {

    suspend fun addPassag(nroMatric: Long): Boolean

    suspend fun addPassag(nroMatric: Long, idMov: Long): Boolean

    suspend fun clearPassag(): Boolean

    suspend fun deletePassag(pos: Int): Boolean

    suspend fun deletePassag(pos: Int, idMov: Long): Boolean

    suspend fun deletePassag(idMov: Long): Boolean

    suspend fun listPassag(): List<MovEquipProprioPassag>

    suspend fun listPassag(idMov: Long): List<MovEquipProprioPassag>

    suspend fun savePassag(idMov: Long): Boolean

}