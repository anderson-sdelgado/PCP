package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface SetStatusSentMovVisitTerc {
    suspend operator fun invoke(list: List<MovEquipVisitTerc>): Result<Boolean>
}

class ISetStatusSentMovVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
): SetStatusSentMovVisitTerc {

    override suspend fun invoke(list: List<MovEquipVisitTerc>): Result<Boolean> {
        return movEquipVisitTercRepository.setSent(list)
    }

}