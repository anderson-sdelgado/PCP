package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository

interface SetStatusSentMovProprio {
    suspend operator fun invoke(list: List<MovEquipProprio>): Result<Boolean>
}

class ISetStatusSentMovProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository
): SetStatusSentMovProprio {

    override suspend fun invoke(list: List<MovEquipProprio>): Result<Boolean> {
        val result = movEquipProprioRepository.setSent(list)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetStatusSentMovProprio",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}