package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface CheckSendMovResidencia {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): CheckSendMovResidencia {

    override suspend fun invoke(): Result<Boolean> {
        return movEquipResidenciaRepository.checkSend()
    }

}