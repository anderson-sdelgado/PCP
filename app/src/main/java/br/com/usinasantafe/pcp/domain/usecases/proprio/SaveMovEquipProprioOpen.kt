package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioSegRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.domain.usecases.common.SetStatusSendConfig
import br.com.usinasantafe.pcp.utils.StatusSend
import javax.inject.Inject

interface SaveMovEquipProprioOpen {
    suspend operator fun invoke(): Boolean
}

class SaveMovEquipProprioOpenImpl @Inject constructor(
    private val configRepository: ConfigRepository,
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioSegRepository: MovEquipProprioSegRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val setStatusSendConfig: SetStatusSendConfig,
    private val startProcessSendData: StartProcessSendData,
): SaveMovEquipProprioOpen {

    override suspend fun invoke(): Boolean {
        val config = configRepository.getConfig()
        val idMov = movEquipProprioRepository.saveMovEquipProprio(config.matricVigia!!, config.idLocal!!)
        if(idMov == 0L) return false
        if(!movEquipProprioSegRepository.saveEquipSeg(idMov)) return false
        if(!movEquipProprioPassagRepository.savePassag(idMov)) return false
        setStatusSendConfig(StatusSend.SEND)
        startProcessSendData()
        return true
    }

}