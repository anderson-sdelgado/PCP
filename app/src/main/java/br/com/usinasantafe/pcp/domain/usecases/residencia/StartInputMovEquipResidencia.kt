package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface StartInputMovEquipResidencia {
    suspend operator fun invoke(movEquipResidencia: MovEquipResidencia? = null): Result<Boolean>
}

class IStartInputMovEquipResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): StartInputMovEquipResidencia {

    override suspend fun invoke(movEquipResidencia: MovEquipResidencia?): Result<Boolean> {
        try {
            val result = movEquipResidenciaRepository.start()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartInputMovEquipResidencia",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IStartInputMovEquipResidencia",
                message = "-",
                cause = e
            )
        }
    }

}