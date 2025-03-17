package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetMatricColabMovChave {
    suspend operator fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetMatricColabMovChave(
    private val movChaveRepository: MovChaveRepository,
    private val startProcessSendData: StartProcessSendData
): SetMatricColabMovChave {

    override suspend fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultSet = movChaveRepository.setMatricColab(
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
                    function = "ISetMatricColabMovChave",
                    cause = e
                )
            )
        }
    }

}