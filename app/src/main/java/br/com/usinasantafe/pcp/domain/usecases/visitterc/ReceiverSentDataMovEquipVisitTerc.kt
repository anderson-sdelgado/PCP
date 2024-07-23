package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface ReceiverSentDataMovEquipVisitTerc {
    suspend operator fun invoke(movEquipVisitTercList: List<MovEquipVisitTerc>): Boolean
}

class ReceiverSentDataMovEquipVisitTercImpl @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
) : ReceiverSentDataMovEquipVisitTerc {

    override suspend fun invoke(movEquipVisitTercList: List<MovEquipVisitTerc>): Boolean {
        return movEquipVisitTercRepository.receiverSentMovEquipVisitTerc(movEquipVisitTercList)
    }

}