package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.domain.usecases.common.SetStatusSendConfig
import br.com.usinasantafe.pcp.utils.StatusSend
import javax.inject.Inject

interface SaveMovEquipVisitTerc {
    suspend operator fun invoke(): Boolean
    suspend operator fun invoke(movEquipVisitTerc: MovEquipVisitTerc): Boolean
}

class SaveMovEquipVisitTercImpl @Inject constructor(
    private val configRepository: ConfigRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val setStatusSendConfig: SetStatusSendConfig,
    private val startProcessSendData: StartProcessSendData,
): SaveMovEquipVisitTerc {

    override suspend fun invoke(): Boolean {
        val config = configRepository.getConfig()
        val idMov = movEquipVisitTercRepository.saveMovEquipVisitTerc(config.matricVigia!!, config.idLocal!!)
        if (idMov == 0L) return false
        if(!movEquipVisitTercPassagRepository.savePassag(idMov)) return false
        setStatusSendConfig(StatusSend.SEND)
        startProcessSendData()
        return true
    }

    override suspend fun invoke(movEquipVisitTerc: MovEquipVisitTerc): Boolean {
        val config = configRepository.getConfig()
        val idMov = movEquipVisitTercRepository.saveMovEquipVisitTerc(config.matricVigia!!, config.idLocal!!, movEquipVisitTerc)
        if (idMov == 0L) return false
        val passagList = movEquipVisitTercPassagRepository.listPassag(movEquipVisitTerc.idMovEquipVisitTerc!!)
        if(!movEquipVisitTercPassagRepository.savePassag(idMov, passagList)) return false
        setStatusSendConfig(StatusSend.SEND)
        startProcessSendData()
        return true
    }

}