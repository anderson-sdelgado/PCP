package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import javax.inject.Inject

interface CheckDataSendMovEquipProprio {
    suspend operator fun invoke(): Boolean
}

class CheckDataSendMovEquipProprioImpl @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
): CheckDataSendMovEquipProprio {

    override suspend fun invoke(): Boolean {
        return movEquipProprioRepository.checkMovSend()
    }

}