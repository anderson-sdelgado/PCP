package br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import javax.inject.Inject

interface CheckSendMovResidencia {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovResidencia @Inject constructor(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): CheckSendMovResidencia {

    override suspend fun invoke(): Result<Boolean> {
        val result = movEquipResidenciaRepository.checkSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICheckSendMovResidencia",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}