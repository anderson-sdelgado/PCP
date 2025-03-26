package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface CheckSendMovVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
): CheckSendMovVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        val result = movEquipVisitTercRepository.checkSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICheckSendMovResidencia",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}