package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipProprioPassagRepository {
    suspend fun add(matricColab: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun delete(id: Int): EmptyResult
    suspend fun delete(matricColab: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun list(flowApp: FlowApp, id: Int): Result<List<MovEquipProprioPassag>>
    suspend fun save(id: Int): EmptyResult
}