package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository

interface GetObservMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<String?>
}

class IGetObservMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): GetObservMovChaveEquip {

    override suspend fun invoke(id: Int): Result<String?> {
        val result = movChaveEquipRepository.getObserv(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetObservMovChaveEquip",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}