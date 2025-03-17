package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
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
        return movEquipVisitTercRepository.setClose(id)
    }

}