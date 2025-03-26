package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.TypeMovEquip

interface SaveMovEquipVisitTerc {
    suspend operator fun invoke(
        typeMov: TypeMovEquip,
        id: Int
    ): Result<Boolean>
}

class ISaveMovEquipVisitTerc(
    private val configRepository: ConfigRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val startProcessSendData: StartProcessSendData
) : SaveMovEquipVisitTerc {

    override suspend fun invoke(
        typeMov: TypeMovEquip,
        id: Int
    ): Result<Boolean> {
        try {
            if (typeMov == TypeMovEquip.OUTPUT) {
                val resultClose = movEquipVisitTercRepository.setOutside(id)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISaveMovEquipVisitTerc",
                        message = e.message,
                        cause = e
                    )
                }
            }
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovEquipVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultConfig.getOrNull()!!
            val resultSave = movEquipVisitTercRepository.save(
                config.matricVigia!!,
                config.idLocal!!
            )
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovEquipVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val idSave = resultSave.getOrNull()!!
            val resultSavePassag = movEquipVisitTercPassagRepository.save(idSave)
            if (resultSavePassag.isFailure) {
                val e = resultSavePassag.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovEquipVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            if (typeMov == TypeMovEquip.OUTPUT) {
                val resultClose = movEquipVisitTercRepository.setOutside(idSave)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISaveMovEquipVisitTerc",
                        message = e.message,
                        cause = e
                    )
                }
            }
            startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISaveMovEquipVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}