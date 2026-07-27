package br.com.usinasantafe.pcp.domain.usecases.chaveEquip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.lib.FlowApp
import javax.inject.Inject

interface SetMatricColabMovChaveEquip {
    suspend operator fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetMatricColabMovChaveEquip @Inject constructor(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val startProcessSendData: StartProcessSendData
): SetMatricColabMovChaveEquip {

    override suspend fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultSet = movChaveEquipRepository.setMatricColab(
                matricColab = matricColab.toInt(),
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure) {
                val e = resultSet.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetMatricColabMovChaveEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetMatricColabMovChaveEquip",
                message = "-",
                cause = e
            )
        }
    }

}