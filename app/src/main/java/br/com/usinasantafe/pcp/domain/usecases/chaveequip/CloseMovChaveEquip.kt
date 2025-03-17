package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository

interface CloseMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class ICloseMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): CloseMovChaveEquip {

    override suspend fun invoke(id: Int): Result<Boolean> {
        return movChaveEquipRepository.setClose(id)
    }

}