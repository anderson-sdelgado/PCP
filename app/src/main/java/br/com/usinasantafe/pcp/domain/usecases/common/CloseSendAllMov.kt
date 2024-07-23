package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.StatusData
import javax.inject.Inject

interface CloseSendAllMov {
    suspend operator fun invoke(): Boolean
}

class CloseSendAllMovImpl @Inject constructor(
    private val configRepository: ConfigRepository,
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val setStatusSendConfig: SetStatusSendConfig,
    private val startProcessSendData: StartProcessSendData,
): CloseSendAllMov {

    override suspend fun invoke(): Boolean {
        try {
            var check = true
            val movEquipProprioList = movEquipProprioRepository.listMovEquipProprioOpen()
            for (movEquipProprio in movEquipProprioList) {
                check = movEquipProprioRepository.setStatusCloseMov(movEquipProprio)
            }
            val movEquipVisitTercList = movEquipVisitTercRepository.listMovEquipVisitTercOpen()
            for (movEquipVisitTerc in movEquipVisitTercList) {
                check = movEquipVisitTercRepository.setStatusCloseMov(movEquipVisitTerc)
            }
            val movEquipList = movEquipResidenciaRepository.listMovEquipResidenciaOpen()
            for (movEquip in movEquipList) {
                check = movEquipResidenciaRepository.setStatusCloseMov(movEquip)
            }
            if(!check) return false
            val config = configRepository.getConfig()
            config.statusApont = StatusData.CLOSE
            configRepository.saveConfig(config)
            setStatusSendConfig(StatusSend.SEND)
            startProcessSendData()
        } catch (exception: Exception) {
            return false
        }
        return true
    }

}