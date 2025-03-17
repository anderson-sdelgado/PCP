package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import java.util.Date

interface StartOutputMovEquipResidencia {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class IStartOutputMovEquipResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : StartOutputMovEquipResidencia {

    override suspend fun invoke(id: Int): Result<Boolean> {
        try {
            val resultMov = movEquipResidenciaRepository.get(id)
            if (resultMov.isFailure)
                return Result.failure(resultMov.exceptionOrNull()!!)
            val movEquipResidencia = resultMov.getOrNull()!!
            movEquipResidencia.observMovEquipResidencia = null
            movEquipResidencia.tipoMovEquipResidencia = TypeMovEquip.OUTPUT
            movEquipResidencia.dthrMovEquipResidencia = Date()
            movEquipResidencia.statusMovEquipForeignerResidencia = StatusForeigner.OUTSIDE
            val resultStart = movEquipResidenciaRepository.start(movEquipResidencia)
            if (resultStart.isFailure)
                return Result.failure(resultStart.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "StartMovEquipResidenciaImpl",
                    cause = e.cause
                )
            )
        }
    }

}