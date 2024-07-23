package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.common.SetStatusSendConfig
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import javax.inject.Inject

interface CloseSendAllMovProprio {
    suspend operator fun invoke(): Boolean
}

class CloseSendAllMovProprioImpl @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val setStatusSendConfig: SetStatusSendConfig,
    private val startProcessSendData: StartProcessSendData,
): CloseSendAllMovProprio {

    override suspend fun invoke(): Boolean {
        try{
            var check = true
            val movEquipProprioList = movEquipProprioRepository.listMovEquipProprioOpen()
            for (movEquipProprio in movEquipProprioList) {
                check = movEquipProprioRepository.setStatusCloseMov(movEquipProprio)
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