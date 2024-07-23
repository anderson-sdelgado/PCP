package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface SetDestinoVisitTerc {
    suspend operator fun invoke(destino: String, flowApp: FlowApp, pos: Int): Boolean
}

class SetDestinoVisitTercImpl @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
): SetDestinoVisitTerc {

    override suspend fun invoke(destino: String, flowApp: FlowApp, pos: Int): Boolean {
        return when(flowApp) {
            FlowApp.ADD -> movEquipVisitTercRepository.setDestinoMovEquipVisitTerc(destino)
            FlowApp.CHANGE -> {
                val movEquip = movEquipVisitTercRepository.listMovEquipVisitTercOpen()[pos]
                movEquipVisitTercRepository.updateDestinoMovEquipVisitTerc(destino, movEquip)
            }
        }
    }

}