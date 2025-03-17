package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface SetStatusSentMovResidencia {
    suspend operator fun invoke(list: List<MovEquipResidencia>): Result<Boolean>
}

class ISetStatusSentMovResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
): SetStatusSentMovResidencia {

    override suspend fun invoke(list: List<MovEquipResidencia>): Result<Boolean> {
        return movEquipResidenciaRepository.setSent(list)
    }

}