package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface SetStatusSentMovResidencia {
    suspend operator fun invoke(list: List<MovEquipResidencia>): Result<Boolean>
}

class ISetStatusSentMovResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
): SetStatusSentMovResidencia {

    override suspend fun invoke(list: List<MovEquipResidencia>): Result<Boolean> {
        val result = movEquipResidenciaRepository.setSent(list)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetStatusSentMovResidencia",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}