package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository

interface CheckSendMovChaveEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): CheckSendMovChaveEquip {

    override suspend fun invoke(): Result<Boolean> {
        val result = movChaveEquipRepository.checkSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICheckSendMovChaveEquip",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}