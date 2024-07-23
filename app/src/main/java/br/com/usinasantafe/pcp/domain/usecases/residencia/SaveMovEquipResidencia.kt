package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.domain.usecases.common.SetStatusSendConfig
import br.com.usinasantafe.pcp.utils.StatusSend
import javax.inject.Inject

interface SaveMovEquipResidencia {
    suspend operator fun invoke(): Boolean
    suspend operator fun invoke(movEquipResidencia: MovEquipResidencia): Boolean
}

class SaveMovEquipResidenciaImpl @Inject constructor(
    private val configRepository: ConfigRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val setStatusSendConfig: SetStatusSendConfig,
    private val startProcessSendData: StartProcessSendData,
): SaveMovEquipResidencia {

    override suspend fun invoke(): Boolean {
        val config = configRepository.getConfig()
        val result = (movEquipResidenciaRepository.saveMovEquipResidencia(config.matricVigia!!, config.idLocal!!) != 0L)
        if(result) {
            setStatusSendConfig(StatusSend.SEND)
            startProcessSendData()
        }
        return result
    }

    override suspend fun invoke(movEquipResidencia: MovEquipResidencia): Boolean {
        val config = configRepository.getConfig()
        val result = (movEquipResidenciaRepository.saveMovEquipResidencia(config.matricVigia!!, config.idLocal!!, movEquipResidencia) != 0L)
        if(result) {
            setStatusSendConfig(StatusSend.SEND)
            startProcessSendData()
        }
        return result
    }

}