package br.com.usinasantafe.pcp.domain.usecases.chaveEquip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import javax.inject.Inject

interface StartReceiptMovChaveEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class IStartReceiptMovChaveEquip @Inject constructor(
    private val movChaveEquipRepository: MovChaveEquipRepository
): StartReceiptMovChaveEquip {

    override suspend fun invoke(): Result<Boolean> {
        val result = movChaveEquipRepository.start()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IStartReceiptMovChaveEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}