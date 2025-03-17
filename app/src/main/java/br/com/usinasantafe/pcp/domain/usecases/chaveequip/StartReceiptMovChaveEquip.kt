package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository

interface StartReceiptMovChaveEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class IStartReceiptMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): StartReceiptMovChaveEquip {

    override suspend fun invoke(): Result<Boolean> {
        return movChaveEquipRepository.start()
    }

}