package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.utils.token
import javax.inject.Inject

interface SendDataMovEquipResidencia {
    suspend operator fun invoke(): Result<List<MovEquipResidencia>>
}

class SendDataMovEquipResidenciaImpl @Inject constructor(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val configRepository: ConfigRepository,
) : SendDataMovEquipResidencia {

    override suspend fun invoke(): Result<List<MovEquipResidencia>> {
        val listMovEquipResidenciaSend = movEquipResidenciaRepository.listMovEquipResidenciaSend()
        val config = configRepository.getConfig()
        return movEquipResidenciaRepository.sendMovEquipResidencia(
            listMovEquipResidenciaSend,
            config.nroAparelhoConfig!!,
            token(nroAparelho = config.nroAparelhoConfig!!, version = config.version!!, idBD = config.idBDConfig!!),
        )
    }

}