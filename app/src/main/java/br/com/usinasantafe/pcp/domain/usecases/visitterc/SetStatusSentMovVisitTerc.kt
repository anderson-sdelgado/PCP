package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface SetStatusSentMovVisitTerc {
    suspend operator fun invoke(list: List<MovEquipVisitTerc>): Result<Boolean>
}

class ISetStatusSentMovVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
): SetStatusSentMovVisitTerc {

    override suspend fun invoke(list: List<MovEquipVisitTerc>): Result<Boolean> {
        val result = movEquipVisitTercRepository.setSent(list)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetStatusSentMovVisitTerc",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}