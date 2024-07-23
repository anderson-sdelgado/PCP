package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface StartMovEquipVisitTerc {
    suspend operator fun invoke(): Boolean
}

class StartMovEquipVisitTercImpl @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
): StartMovEquipVisitTerc {
    override suspend fun invoke(): Boolean {
        return try {
            movEquipVisitTercRepository.startMovEquipVisitTerc()
        } catch (e: Exception){
            false
        }
    }

}