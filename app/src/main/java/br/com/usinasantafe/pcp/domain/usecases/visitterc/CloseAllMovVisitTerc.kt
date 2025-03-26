package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface CloseAllMovVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
): CloseAllMovVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultVisitTercList = movEquipVisitTercRepository.listOpen()
            if (resultVisitTercList.isFailure) {
                val e = resultVisitTercList.exceptionOrNull()!!
                return resultFailure(
                    context = "ICloseAllMovVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = resultVisitTercList.getOrNull()!!
            for(entity in entityList){
                val resultClose = movEquipVisitTercRepository.setClose(entity.idMovEquipVisitTerc!!)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ICloseAllMovVisitTerc",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICloseAllMovVisitTerc",
                message = "-",
                cause = e
            )
        }

    }

}