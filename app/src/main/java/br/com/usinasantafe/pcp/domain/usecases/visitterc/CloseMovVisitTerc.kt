package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface CloseMovVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class ICloseMovVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
): CloseMovVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        val result = movEquipVisitTercRepository.setClose(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICloseMovVisitTerc",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}