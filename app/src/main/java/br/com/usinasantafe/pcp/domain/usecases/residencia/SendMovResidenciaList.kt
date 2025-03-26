package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.utils.token

interface SendMovResidenciaList {
    suspend operator fun invoke(): Result<List<MovEquipResidencia>>
}

class ISendMovResidenciaList(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val configRepository: ConfigRepository,
): SendMovResidenciaList {

    override suspend fun invoke(): Result<List<MovEquipResidencia>> {
        try {
            val resultListSend = movEquipResidenciaRepository.listSend()
            if (resultListSend.isFailure) {
                val e = resultListSend.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovResidenciaList",
                    message = e.message,
                    cause = e
                )
            }
            val listSend = resultListSend.getOrNull()!!
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovResidenciaList",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultConfig.getOrNull()!!
            val token = token(
                number = config.number!!,
                version = config.version!!,
                idBD = config.idBD!!
            )
            val resultSend = movEquipResidenciaRepository.send(
                list = listSend,
                number = config.number!!,
                token = token
            )
            if (resultSend.isFailure) {
                val e = resultSend.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovResidenciaList",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultSend.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISendMovResidenciaList",
                message = "-",
                cause = e
            )
        }
    }

}