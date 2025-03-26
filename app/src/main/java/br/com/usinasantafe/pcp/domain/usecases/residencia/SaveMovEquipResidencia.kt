package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.TypeMovEquip

interface SaveMovEquipResidencia {
    suspend operator fun invoke(
        typeMov: TypeMovEquip,
        id: Int
    ): Result<Boolean>
}

class ISaveMovEquipResidencia(
    private val configRepository: ConfigRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val startProcessSendData: StartProcessSendData
) : SaveMovEquipResidencia {

    override suspend fun invoke(
        typeMov: TypeMovEquip,
        id: Int
    ): Result<Boolean> {
        try {
            if (typeMov == TypeMovEquip.OUTPUT) {
                val resultClose = movEquipResidenciaRepository.setOutside(id)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISaveMovEquipResidencia",
                        message = e.message,
                        cause = e
                    )
                }
            }
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovEquipResidencia",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultConfig.getOrNull()!!
            val resultSave = movEquipResidenciaRepository.save(
                config.matricVigia!!,
                config.idLocal!!
            )
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovEquipResidencia",
                    message = e.message,
                    cause = e
                )
            }
            val idSave = resultSave.getOrNull()!!
            if (typeMov == TypeMovEquip.OUTPUT) {
                val resultClose = movEquipResidenciaRepository.setOutside(idSave)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISaveMovEquipResidencia",
                        message = e.message,
                        cause = e
                    )
                }
            }
            startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISaveMovEquipResidencia",
                message = "-",
                cause = e
            )
        }
    }

}