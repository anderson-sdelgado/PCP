package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.utils.FlowApp

interface DeleteEquipSeg {
    suspend operator fun invoke(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int,
    ): Result<Boolean>
}

class IDeleteEquipSeg(
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository
) : DeleteEquipSeg {

    override suspend fun invoke(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int,
    ): Result<Boolean> {
        return movEquipProprioEquipSegRepository.delete(
            idEquip = idEquip,
            flowApp = flowApp,
            id = id
        )
    }

}