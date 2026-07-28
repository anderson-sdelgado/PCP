package br.com.usinasantafe.pcp.domain.usecases.chaveEquip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import javax.inject.Inject

interface CheckSendMovChaveEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovChaveEquip @Inject constructor(
    private val movChaveEquipRepository: MovChaveEquipRepository
): CheckSendMovChaveEquip {

    override suspend fun invoke(): Result<Boolean> {
        val result = movChaveEquipRepository.hasSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICheckSendMovChaveEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}