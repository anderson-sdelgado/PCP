package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.common.SetStatusSendConfig
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import javax.inject.Inject

interface CloseSendAllMovVisitTerc {
    suspend operator fun invoke(): Boolean
}

class CloseSendAllMovVisitTercImpl @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val setStatusSendConfig: SetStatusSendConfig,
    private val startProcessSendData: StartProcessSendData,
): CloseSendAllMovVisitTerc {

    override suspend fun invoke(): Boolean {
        try{
            var check = true
            val movEquipVisitTercList = movEquipVisitTercRepository.listMovEquipVisitTercOpen()
            for (movEquipVisitTerc in movEquipVisitTercList) {
                check = movEquipVisitTercRepository.setStatusCloseMov(movEquipVisitTerc)
            }
            if(!check) return false
            setStatusSendConfig(StatusSend.SEND)
            startProcessSendData()
        } catch (exception: Exception) {
            return false
        }
        return true
    }
}