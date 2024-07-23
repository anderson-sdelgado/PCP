package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.utils.dateToDelete
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioSegRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface DeleteMovSent {
    suspend operator fun invoke()
}

class DeleteMovSentImpl @Inject constructor (
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val movEquipProprioSegRepository: MovEquipProprioSegRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
): DeleteMovSent {

    override suspend fun invoke() {
        val movEquipProprioList = movEquipProprioRepository.listMovEquipProprioSent()
        for (movEquipProprio in movEquipProprioList) {
            if(movEquipProprio.dthrMovEquipProprio < dateToDelete()) {
                movEquipProprioPassagRepository.deletePassag(movEquipProprio.idMovEquipProprio!!)
                movEquipProprioSegRepository.deleteEquipSeg(movEquipProprio.idMovEquipProprio!!)
                movEquipProprioRepository.deleteMovEquipProprio(movEquipProprio)
            }
        }
        val movEquipVisitTercList = movEquipVisitTercRepository.listMovEquipVisitTercSent()
        for (movEquipVisitTerc in movEquipVisitTercList) {
            if(movEquipVisitTerc.dthrMovEquipVisitTerc < dateToDelete()) {
                movEquipVisitTercPassagRepository.deletePassag(movEquipVisitTerc.idMovEquipVisitTerc!!)
                movEquipVisitTercRepository.deleteMovEquipVisitTerc(movEquipVisitTerc)
            }
        }
        val movEquipResidenciaList = movEquipResidenciaRepository.listMovEquipResidenciaSent()
        for (movEquipResidencia in movEquipResidenciaList) {
            if(movEquipResidencia.dthrMovEquipResidencia < dateToDelete()) {
                movEquipResidenciaRepository.deleteMovEquipResidencia(movEquipResidencia)
            }
        }
    }

}