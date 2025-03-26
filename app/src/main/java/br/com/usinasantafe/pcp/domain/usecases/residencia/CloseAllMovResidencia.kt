package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface CloseAllMovResidencia {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): CloseAllMovResidencia {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultList = movEquipResidenciaRepository.listOpen()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "ICloseAllMovResidencia",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = resultList.getOrNull()!!
            for(entity in entityList){
                val resultClose = movEquipResidenciaRepository.setClose(entity.idMovEquipResidencia!!)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ICloseAllMovResidencia",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICloseAllMovResidencia",
                message = "-",
                cause = e
            )
        }
    }

}