package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData

interface SaveMovEquipProprio {
    suspend operator fun invoke(): Result<Boolean>
}

class ISaveMovEquipProprio(
    private val configRepository: ConfigRepository,
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val startProcessSendData: StartProcessSendData
) : SaveMovEquipProprio {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovEquipProprio",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultConfig.getOrNull()!!
            val resultSave = movEquipProprioRepository.save(
                config.matricVigia!!,
                config.idLocal!!
            )
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovEquipProprio",
                    message = e.message,
                    cause = e
                )
            }
            val id = resultSave.getOrNull()!!
            val resultSavePassag = movEquipProprioPassagRepository.save(id)
            if (resultSavePassag.isFailure) {
                val e = resultSavePassag.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovEquipProprio",
                    message = e.message,
                    cause = e
                )
            }
            val resultSaveEquipSeg = movEquipProprioEquipSegRepository.save(id)
            if (resultSaveEquipSeg.isFailure) {
                val e = resultSaveEquipSeg.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovEquipProprio",
                    message = e.message,
                    cause = e
                )
            }
            startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISaveMovEquipProprio",
                message = "-",
                cause = e
            )
        }
    }

}