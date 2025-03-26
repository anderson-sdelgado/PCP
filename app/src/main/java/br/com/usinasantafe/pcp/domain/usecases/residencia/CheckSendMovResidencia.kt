package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface CheckSendMovResidencia {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): CheckSendMovResidencia {

    override suspend fun invoke(): Result<Boolean> {
        val result = movEquipResidenciaRepository.checkSend()
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