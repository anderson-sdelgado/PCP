package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcp.utils.FlowApp

interface MovEquipProprioEquipSegRepository {
    suspend fun add(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun clean(): Result<Boolean>
    suspend fun delete(id: Int): Result<Boolean>
    suspend fun delete(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>


    suspend fun list(
        flowApp: FlowApp,
        id: Int
    ): Result<List<MovEquipProprioEquipSeg>>

    suspend fun save(
        id: Int
    ): Result<Boolean>
}