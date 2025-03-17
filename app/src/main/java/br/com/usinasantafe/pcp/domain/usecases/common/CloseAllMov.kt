package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
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
            if(resultProprioList.isFailure)
                return Result.failure(resultProprioList.exceptionOrNull()!!)
            val movEquipProprioList = resultProprioList.getOrNull()!!
            for(movEquipProprio in movEquipProprioList){
                val resultClose = movEquipProprioRepository.setClose(movEquipProprio.idMovEquipProprio!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            val resultVisitTercList = movEquipVisitTercRepository.listOpen()
            if(resultVisitTercList.isFailure)
                return Result.failure(resultVisitTercList.exceptionOrNull()!!)
            val movEquipVisitTercList = resultVisitTercList.getOrNull()!!
            for(movEquipVisitTerc in movEquipVisitTercList){
                val resultClose = movEquipVisitTercRepository.setClose(movEquipVisitTerc.idMovEquipVisitTerc!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            val resultResidenciaList = movEquipResidenciaRepository.listOpen()
            if(resultResidenciaList.isFailure)
                return Result.failure(resultResidenciaList.exceptionOrNull()!!)
            val movEquipResidenciaList = resultResidenciaList.getOrNull()!!
            for(movEquipResidencia in movEquipResidenciaList){
                val resultClose = movEquipResidenciaRepository.setClose(movEquipResidencia.idMovEquipResidencia!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            val resultChaveList = movChaveRepository.listOpen()
            if(resultChaveList.isFailure)
                return Result.failure(resultChaveList.exceptionOrNull()!!)
            val movChaveList = resultChaveList.getOrNull()!!
            for(movChave in movChaveList){
                val resultClose = movChaveRepository.setClose(movChave.idMovChave!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            val resultChaveEquipList = movChaveEquipRepository.listOpen()
            if(resultChaveEquipList.isFailure)
                return Result.failure(resultChaveEquipList.exceptionOrNull()!!)
            val movChaveEquipList = resultChaveEquipList.getOrNull()!!
            for(movChaveEquip in movChaveEquipList){
                val resultClose = movChaveEquipRepository.setClose(movChaveEquip.idMovChaveEquip!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "CloseAllMovOpenImpl",
                    cause = e
                )
            )
        }
    }

}