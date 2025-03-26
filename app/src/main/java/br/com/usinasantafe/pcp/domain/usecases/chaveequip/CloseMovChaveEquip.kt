package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository

interface CloseMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class ICloseMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): CloseMovChaveEquip {

    override suspend fun invoke(id: Int): Result<Boolean> {
        val result = movChaveEquipRepository.setClose(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICloseMovChaveEquip",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}