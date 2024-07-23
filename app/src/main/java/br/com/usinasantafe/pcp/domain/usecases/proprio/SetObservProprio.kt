package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.domain.usecases.common.SetStatusSendConfig
import br.com.usinasantafe.pcp.utils.StatusSend
import javax.inject.Inject

interface SetObservProprio {
    suspend operator fun invoke(observ: String?, flowApp: FlowApp, pos: Int): Boolean
}

class SetObservProprioImpl @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val saveMovEquipProprioOpen: SaveMovEquipProprioOpen,
): SetObservProprio {

    override suspend fun invoke(observ: String?, flowApp: FlowApp, pos: Int): Boolean {
        when(flowApp) {
            FlowApp.ADD -> {
                if(!movEquipProprioRepository.setObservMovEquipProprio(observ)) return false
                return saveMovEquipProprioOpen()
            }
            FlowApp.CHANGE -> {
                val movEquip = movEquipProprioRepository.listMovEquipProprioOpen()[pos]
                movEquipProprioRepository.updateObservMovEquipProprio(observ, movEquip)
                return true
            }
        }
    }

}