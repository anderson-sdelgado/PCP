package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.utils.FlowApp

interface DeletePassagColab {
    suspend operator fun invoke(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class IDeletePassagColab(
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository
): DeletePassagColab {

    override suspend fun invoke(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return movEquipProprioPassagRepository.delete(
            matricColab = matricColab,
            flowApp = flowApp,
            id = id
        )
    }

}