package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.utils.FlowApp

interface DeletePassagVisitTerc {
    suspend operator fun invoke(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class IDeletePassagVisitTerc(
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository
) : DeletePassagVisitTerc {

    override suspend fun invoke(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        val result = movEquipVisitTercPassagRepository.delete(
            idVisitTerc = idVisitTerc,
            flowApp = flowApp,
            id = id
        )
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IDeletePassagVisitTerc",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}