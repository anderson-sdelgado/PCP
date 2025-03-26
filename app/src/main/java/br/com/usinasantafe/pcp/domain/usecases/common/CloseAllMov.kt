package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface CloseAllMov {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMov(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val movChaveRepository: MovChaveRepository,
    private val movChaveEquipRepository: MovChaveEquipRepository
): CloseAllMov {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultProprioList = movEquipProprioRepository.listOpen()
            if (resultProprioList.isFailure) {
                val e = resultProprioList.exceptionOrNull()!!
                return resultFailure(
                    context = "ICloseAllMov",
                    message = e.message,
                    cause = e
                )
            }
            val movEquipProprioList = resultProprioList.getOrNull()!!
            for(movEquipProprio in movEquipProprioList){
                val resultClose = movEquipProprioRepository.setClose(movEquipProprio.idMovEquipProprio!!)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ICloseAllMov",
                        message = e.message,
                        cause = e
                    )
                }
            }
            val resultVisitTercList = movEquipVisitTercRepository.listOpen()
            if (resultVisitTercList.isFailure) {
                val e = resultVisitTercList.exceptionOrNull()!!
                return resultFailure(
                    context = "ICloseAllMov",
                    message = e.message,
                    cause = e
                )
            }
            val movEquipVisitTercList = resultVisitTercList.getOrNull()!!
            for(movEquipVisitTerc in movEquipVisitTercList){
                val resultClose = movEquipVisitTercRepository.setClose(movEquipVisitTerc.idMovEquipVisitTerc!!)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ICloseAllMov",
                        message = e.message,
                        cause = e
                    )
                }
            }
            val resultResidenciaList = movEquipResidenciaRepository.listOpen()
            if (resultResidenciaList.isFailure) {
                val e = resultResidenciaList.exceptionOrNull()!!
                return resultFailure(
                    context = "ICloseAllMov",
                    message = e.message,
                    cause = e
                )
            }
            val movEquipResidenciaList = resultResidenciaList.getOrNull()!!
            for(movEquipResidencia in movEquipResidenciaList){
                val resultClose = movEquipResidenciaRepository.setClose(movEquipResidencia.idMovEquipResidencia!!)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ICloseAllMov",
                        message = e.message,
                        cause = e
                    )
                }
            }
            val resultChaveList = movChaveRepository.listOpen()
            if (resultChaveList.isFailure) {
                val e = resultChaveList.exceptionOrNull()!!
                return resultFailure(
                    context = "ICloseAllMov",
                    message = e.message,
                    cause = e
                )
            }
            val movChaveList = resultChaveList.getOrNull()!!
            for(movChave in movChaveList){
                val resultClose = movChaveRepository.setClose(movChave.idMovChave!!)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ICloseAllMov",
                        message = e.message,
                        cause = e
                    )
                }
            }
            val resultChaveEquipList = movChaveEquipRepository.listOpen()
            if (resultChaveEquipList.isFailure) {
                val e = resultChaveEquipList.exceptionOrNull()!!
                return resultFailure(
                    context = "ICloseAllMov",
                    message = e.message,
                    cause = e
                )
            }
            val movChaveEquipList = resultChaveEquipList.getOrNull()!!
            for(movChaveEquip in movChaveEquipList){
                val resultClose = movChaveEquipRepository.setClose(movChaveEquip.idMovChaveEquip!!)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ICloseAllMov",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "ICloseAllMov",
                message = "-",
                cause = e
            )
        }
    }

}