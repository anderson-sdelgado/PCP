package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import javax.inject.Inject

interface ReceiverSentDataMovEquipProprio {
    suspend operator fun invoke(movEquipProprioList: List<MovEquipProprio>): Boolean
}

class ReceiverSentDataMovEquipProprioImpl @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
) : ReceiverSentDataMovEquipProprio {

    override suspend fun invoke(movEquipProprioList: List<MovEquipProprio>): Boolean {
        return movEquipProprioRepository.receiverSentMovEquipProprio(movEquipProprioList)
    }

}