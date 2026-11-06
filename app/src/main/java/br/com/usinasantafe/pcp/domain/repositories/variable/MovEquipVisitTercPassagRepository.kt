package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipVisitTercPassagRepository {
    suspend fun add(idVisitTerc: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun delete(id: Int): EmptyResult
    suspend fun delete(idVisitTerc: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun list(flowApp: FlowApp, id: Int): Result<List<MovEquipVisitTercPassag>>
    suspend fun save(id: Int): EmptyResult
}