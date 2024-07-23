package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag

interface MovEquipVisitTercPassagRepository {

    suspend fun addPassag(idVisitTerc: Long): Boolean

    suspend fun addPassag(idVisitTerc: Long, idMov: Long): Boolean

    suspend fun deletePassag(pos: Int): Boolean

    suspend fun deletePassag(pos: Int, idMov: Long): Boolean

    suspend fun deletePassag(idMov: Long): Boolean

    suspend fun listPassag(): List<MovEquipVisitTercPassag>

    suspend fun listPassag(idMov: Long): List<MovEquipVisitTercPassag>

    suspend fun savePassag(idMov: Long): Boolean

    suspend fun savePassag(idMov: Long, passagList: List<MovEquipVisitTercPassag>): Boolean

}