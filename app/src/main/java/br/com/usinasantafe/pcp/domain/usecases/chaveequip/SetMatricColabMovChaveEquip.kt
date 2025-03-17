package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetMatricColabMovChaveEquip {
    suspend operator fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetMatricColabMovChaveEquip(
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
            if (resultSet.isFailure)
                return Result.failure(resultSet.exceptionOrNull()!!)
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "ISetMatricColabMovChaveEquip",
                    cause = e
                )
            )
        }
    }

}