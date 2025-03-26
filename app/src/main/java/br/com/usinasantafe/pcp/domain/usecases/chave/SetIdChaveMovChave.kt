package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetIdChaveMovChave {
    suspend operator fun invoke(
        idChave: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetIdChaveMovChave(
    private val movChaveRepository: MovChaveRepository,
    private val startProcessSendData: StartProcessSendData
): SetIdChaveMovChave {

    override suspend fun invoke(
        idChave: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultSet = movChaveRepository.setIdChave(
                idChave = idChave,
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure) {
                val e = resultSet.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdChaveMovChave",
                    message = e.message,
                    cause = e
                )
            }
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetIdChaveMovChave",
                message = "-",
                cause = e
            )
        }
    }

}