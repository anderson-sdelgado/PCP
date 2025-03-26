package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.utils.token

interface SendMovChaveEquipList {
    suspend operator fun invoke(): Result<List<MovChaveEquip>>
}

class ISendMovChaveEquipList(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val configRepository: ConfigRepository
): SendMovChaveEquipList {

    override suspend fun invoke(): Result<List<MovChaveEquip>> {
        try {
            val resultListSend = movChaveEquipRepository.listSend()
            if (resultListSend.isFailure) {
                val e = resultListSend.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovChaveEquipList",
                    message = e.message,
                    cause = e
                )
            }
            val listSend = resultListSend.getOrNull()!!
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovChaveEquipList",
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
            val resultSend = movChaveEquipRepository.send(
                list = listSend,
                number = config.number!!,
                token = token
            )
            if (resultSend.isFailure) {
                val e = resultSend.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendMovChaveEquipList",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultSend.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISendMovChaveEquipList",
                message = "-",
                cause = e
            )
        }
    }

}