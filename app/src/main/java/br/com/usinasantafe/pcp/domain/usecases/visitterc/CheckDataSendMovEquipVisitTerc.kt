package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface CheckDataSendMovEquipVisitTerc {
    suspend operator fun invoke(): Boolean
}

class CheckDataSendMovEquipVisitTercImpl @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
): CheckDataSendMovEquipVisitTerc {

    override suspend fun invoke(): Boolean {
        return movEquipVisitTercRepository.checkMovSend()
    }

}