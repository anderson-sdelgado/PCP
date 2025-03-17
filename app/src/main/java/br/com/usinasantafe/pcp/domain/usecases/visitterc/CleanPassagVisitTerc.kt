package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository

interface CleanPassagVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanPassagVisitTerc(
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository
): CleanPassagVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        return movEquipVisitTercPassagRepository.clear()
    }

}