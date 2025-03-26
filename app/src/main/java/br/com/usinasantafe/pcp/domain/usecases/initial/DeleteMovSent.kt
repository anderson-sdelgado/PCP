package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.dateToDelete

interface DeleteMovSent {
    suspend operator fun invoke(): Result<Boolean>
}

class IDeleteMovSent(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
): DeleteMovSent {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultMovProprioList = movEquipProprioRepository.listSent()
            if (resultMovProprioList.isFailure) {
                val e = resultMovProprioList.exceptionOrNull()!!
                return resultFailure(
                    context = "IDeleteMovSent",
                    message = e.message,
                    cause = e
                )
            }
            val movProprioList = resultMovProprioList.getOrNull()!!
            for(movProprio in movProprioList){
                if(movProprio.dthrMovEquipProprio < dateToDelete()){
                    val resultDelMovProprioPassag =
                        movEquipProprioPassagRepository.delete(movProprio.idMovEquipProprio!!)
                    if (resultDelMovProprioPassag.isFailure) {
                        val e = resultDelMovProprioPassag.exceptionOrNull()!!
                        return resultFailure(
                            context = "IDeleteMovSent",
                            message = e.message,
                            cause = e
                        )
                    }
                    val resultDelMovProprioEquipSeg =
                        movEquipProprioEquipSegRepository.delete(movProprio.idMovEquipProprio!!)
                    if (resultDelMovProprioEquipSeg.isFailure) {
                        val e = resultDelMovProprioEquipSeg.exceptionOrNull()!!
                        return resultFailure(
                            context = "IDeleteMovSent",
                            message = e.message,
                            cause = e
                        )
                    }
                    val resultDelMovProprio =
                        movEquipProprioRepository.delete(movProprio.idMovEquipProprio!!)
                    if (resultDelMovProprio.isFailure) {
                        val e = resultDelMovProprio.exceptionOrNull()!!
                        return resultFailure(
                            context = "IDeleteMovSent",
                            message = e.message,
                            cause = e
                        )
                    }
                }
            }
            val resultMovVisitTercList = movEquipVisitTercRepository.listSent()
            if (resultMovVisitTercList.isFailure) {
                val e = resultMovVisitTercList.exceptionOrNull()!!
                return resultFailure(
                    context = "IDeleteMovSent",
                    message = e.message,
                    cause = e
                )
            }
            val movVisitTercList = resultMovVisitTercList.getOrNull()!!
            for(movVisitTerc in movVisitTercList){
                if(movVisitTerc.dthrMovEquipVisitTerc < dateToDelete()){
                    val resultDelMovVisitTerc = movEquipVisitTercRepository.delete(movVisitTerc.idMovEquipVisitTerc!!)
                    if (resultDelMovVisitTerc.isFailure) {
                        val e = resultDelMovVisitTerc.exceptionOrNull()!!
                        return resultFailure(
                            context = "IDeleteMovSent",
                            message = e.message,
                            cause = e
                        )
                    }
                    val resultDelMovVisitTercPassag = movEquipVisitTercPassagRepository.delete(movVisitTerc.idMovEquipVisitTerc!!)
                    if (resultDelMovVisitTercPassag.isFailure) {
                        val e = resultDelMovVisitTercPassag.exceptionOrNull()!!
                        return resultFailure(
                            context = "IDeleteMovSent",
                            message = e.message,
                            cause = e
                        )
                    }
                }
            }
            val resultMovResidenciaList = movEquipResidenciaRepository.listSent()
            if (resultMovResidenciaList.isFailure) {
                val e = resultMovResidenciaList.exceptionOrNull()!!
                return resultFailure(
                    context = "IDeleteMovSent",
                    message = e.message,
                    cause = e
                )
            }
            val movResidenciaList = resultMovResidenciaList.getOrNull()!!
            for(movResidencia in movResidenciaList){
                if(movResidencia.dthrMovEquipResidencia < dateToDelete()){
                    val resultDelMovResidencia = movEquipResidenciaRepository.delete(movResidencia.idMovEquipResidencia!!)
                    if (resultDelMovResidencia.isFailure) {
                        val e = resultDelMovResidencia.exceptionOrNull()!!
                        return resultFailure(
                            context = "IDeleteMovSent",
                            message = e.message,
                            cause = e
                        )
                    }
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IDeleteMovSent",
                message = "-",
                cause = e
            )
        }
    }

}