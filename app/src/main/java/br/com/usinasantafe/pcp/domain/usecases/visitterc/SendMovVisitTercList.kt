package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.token

interface SendMovVisitTercList {
    suspend operator fun invoke(): Result<List<MovEquipVisitTerc>>
}

class ISendMovVisitTercList(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val configRepository: ConfigRepository,
) : SendMovVisitTercList {

    override suspend fun invoke(): Result<List<MovEquipVisitTerc>> {
        try {
            val resultListSend = movEquipVisitTercRepository.listSend()
            if (resultListSend.isFailure) {
                val e = resultListSend.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovVisitTercList",
                    message = e.message,
                    cause = e
                )
            }
            val listSend = resultListSend.getOrNull()!!
            val listSendFull = listSend.map { entity ->
                val resultListPassag = movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    entity.idMovEquipVisitTerc!!
                )
                if (resultListPassag.isFailure) {
                    val e = resultListPassag.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISendMovVisitTercList",
                        message = e.message,
                        cause = e
                    )
                }
                entity.movEquipVisitTercPassagList = resultListPassag.getOrNull()!!
                return@map entity
            }
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovVisitTercList",
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
            val resultSend = movEquipVisitTercRepository.send(
                list = listSendFull,
                number = config.number!!,
                token = token
            )
            if (resultSend.isFailure) {
                val e = resultSend.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovVisitTercList",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultSend.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISendMovVisitTercList",
                message = "-",
                cause = e
            )
        }
    }

}