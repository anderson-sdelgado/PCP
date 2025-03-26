package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetObservMovChaveEquip {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetObservMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val startProcessSendData: StartProcessSendData
): SetObservMovChaveEquip {

    override suspend fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultSet = movChaveEquipRepository.setObserv(
                observ = observ,
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure) {
                val e = resultSet.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetObservMovChaveEquip",
                    message = e.message,
                    cause = e
                )
            }
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetObservMovChaveEquip",
                message = "-",
                cause = e
            )
        }
    }

}