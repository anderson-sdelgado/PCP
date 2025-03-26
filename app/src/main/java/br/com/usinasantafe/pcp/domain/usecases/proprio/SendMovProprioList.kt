package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.token

interface SendMovProprioList {
    suspend operator fun invoke(): Result<List<MovEquipProprio>>
}

class ISendMovProprioList(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val configRepository: ConfigRepository,
) : SendMovProprioList {

    override suspend fun invoke(): Result<List<MovEquipProprio>> {
        try {
            val resultListSend = movEquipProprioRepository.listSend()
            if (resultListSend.isFailure) {
                val e = resultListSend.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovProprioList",
                    message = e.message,
                    cause = e
                )
            }
            val listSend = resultListSend.getOrNull()!!
            val listSendFull = listSend.map { entity ->
                val resultListEquipSeg = movEquipProprioEquipSegRepository.list(
                    FlowApp.CHANGE,
                    entity.idMovEquipProprio!!
                )
                if (resultListEquipSeg.isFailure) {
                    val e = resultListEquipSeg.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISendMovProprioList",
                        message = e.message,
                        cause = e
                    )
                }
                entity.movEquipProprioEquipSegList = resultListEquipSeg.getOrNull()!!
                val resultListPassag = movEquipProprioPassagRepository.list(
                    FlowApp.CHANGE,
                    entity.idMovEquipProprio!!
                )
                if (resultListPassag.isFailure) {
                    val e = resultListPassag.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISendMovProprioList",
                        message = e.message,
                        cause = e
                    )
                }
                entity.movEquipProprioPassagList = resultListPassag.getOrNull()!!
                return@map entity
            }
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovProprioList",
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
            val resultSend = movEquipProprioRepository.send(
                list = listSendFull,
                number = config.number!!,
                token = token
            )
            if (resultSend.isFailure) {
                val e = resultSend.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovProprioList",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultSend.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISendMovProprioList",
                message = "-",
                cause = e
            )
        }
    }

}