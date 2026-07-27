package br.com.usinasantafe.pcp.domain.usecases.chaveEquip

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import javax.inject.Inject

interface SetStatusSentMovChaveEquip {
    suspend operator fun invoke(list: List<MovChaveEquip>): Result<Boolean>
}

class ISetStatusSentMovChaveEquip @Inject constructor(
    private val movChaveEquipRepository: MovChaveEquipRepository
): SetStatusSentMovChaveEquip {

    override suspend fun invoke(list: List<MovChaveEquip>): Result<Boolean> {
        val result = movChaveEquipRepository.setSent(list)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetStatusSentMovChaveEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}