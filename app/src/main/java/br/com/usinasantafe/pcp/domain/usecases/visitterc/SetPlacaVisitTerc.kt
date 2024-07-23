package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface SetPlacaVisitTerc {
    suspend operator fun invoke(placa: String, flowApp: FlowApp, pos: Int): Boolean
}

class SetPlacaVisitTercImpl @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
): SetPlacaVisitTerc {

    override suspend fun invoke(placa: String, flowApp: FlowApp, pos: Int): Boolean {
        return try {
            when(flowApp){
                FlowApp.ADD -> movEquipVisitTercRepository.setPlacaMovEquipVisitTerc(placa)
                FlowApp.CHANGE -> {
                    val movEquip = movEquipVisitTercRepository.listMovEquipVisitTercOpen()[pos]
                    movEquipVisitTercRepository.updatePlacaMovEquipVisitTerc(placa, movEquip)
                }
            }
        } catch (exception: Exception) {
            false
        }
    }

}