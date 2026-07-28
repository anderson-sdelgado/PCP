package br.com.usinasantafe.pcp.domain.usecases.chaveEquip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.lib.FlowApp
import javax.inject.Inject

interface SetIdEquipMovChaveEquip {
    suspend operator fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetIdEquipMovChaveEquip @Inject constructor(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val equipRepository: EquipRepository,
    private val startProcessSendData: StartProcessSendData
): SetIdEquipMovChaveEquip {

    override suspend fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultId = equipRepository.getIdByNro(nroEquip.toLong())
            if (resultId.isFailure) {
                val e = resultId.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdEquipMovChaveEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idEquip = resultId.getOrNull()!!
            val resultSet = movChaveEquipRepository.setIdEquip(
                idEquip = idEquip,
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure) {
                val e = resultSet.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdEquipMovChaveEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetIdEquipMovChaveEquip",
                message = "-",
                cause = e
            )
        }
    }

}