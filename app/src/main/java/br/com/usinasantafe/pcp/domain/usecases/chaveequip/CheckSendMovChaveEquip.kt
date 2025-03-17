package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository

interface CheckSendMovChaveEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): CheckSendMovChaveEquip {

    override suspend fun invoke(): Result<Boolean> {
        return movChaveEquipRepository.checkSend()
    }

}