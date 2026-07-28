package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipProprioEquipSegRepository {
    suspend fun add(idEquip: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun delete(id: Int): EmptyResult
    suspend fun delete(idEquip: Int, flowApp: FlowApp, id: Int): EmptyResult
    suspend fun list(flowApp: FlowApp, id: Int): Result<List<MovEquipProprioEquipSeg>>
    suspend fun save(id: Int): EmptyResult
}