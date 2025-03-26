package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
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
        val result = movEquipProprioPassagRepository.delete(
            matricColab = matricColab,
            flowApp = flowApp,
            id = id
        )
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IDeletePassagColab",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}