package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcp.utils.FlowApp

interface MovEquipProprioPassagRepository {
    suspend fun add(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun clear(): Result<Boolean>
    suspend fun delete(id: Int): Result<Boolean>
    suspend fun delete(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun list(
        flowApp: FlowApp,
        id: Int
    ): Result<List<MovEquipProprioPassag>>

    suspend fun save(
        id: Int
    ): Result<Boolean>
}