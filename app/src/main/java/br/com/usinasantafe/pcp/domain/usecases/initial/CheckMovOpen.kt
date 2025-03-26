package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface CheckMovOpen {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckMovOpen(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val movChaveRepository: MovChaveRepository,
    private val movChaveEquipRepository: MovChaveEquipRepository
): CheckMovOpen {

    override suspend fun invoke(): Result<Boolean> {
        try {

            val resultCheckProprio = movEquipProprioRepository.checkOpen()
            if (resultCheckProprio.isFailure) {
                val e = resultCheckProprio.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckMovOpen",
                    message = e.message,
                    cause = e
                )
            }
            if(resultCheckProprio.getOrNull()!!) return Result.success(true)
            val resultCheckVisitTerc = movEquipVisitTercRepository.checkOpen()
            if (resultCheckVisitTerc.isFailure) {
                val e = resultCheckVisitTerc.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckMovOpen",
                    message = e.message,
                    cause = e
                )
            }
            if(resultCheckVisitTerc.getOrNull()!!) return Result.success(true)
            val resultCheckResidencia = movEquipResidenciaRepository.checkOpen()
            if (resultCheckResidencia.isFailure) {
                val e = resultCheckResidencia.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckMovOpen",
                    message = e.message,
                    cause = e
                )
            }
            if(resultCheckResidencia.getOrNull()!!) return Result.success(true)
            val resultCheckChave = movChaveRepository.checkOpen()
            if (resultCheckChave.isFailure) {
                val e = resultCheckChave.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckMovOpen",
                    message = e.message,
                    cause = e
                )
            }
            if(resultCheckChave.getOrNull()!!) return Result.success(true)
            val resultCheckChaveEquip = movChaveEquipRepository.checkOpen()
            if (resultCheckChaveEquip.isFailure) {
                val e = resultCheckChaveEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckMovOpen",
                    message = e.message,
                    cause = e
                )
            }
            if(resultCheckChaveEquip.getOrNull()!!) return Result.success(true)
            return Result.success(false)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckMovOpen",
                message = "-",
                cause = e
            )
        }
    }

}