package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetObservProprio {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetObservProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val startProcessSendData: StartProcessSendData
) : SetObservProprio {

    override suspend fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        val resultSet = movEquipProprioRepository.setObserv(
            observ = observ,
            flowApp = flowApp,
            id = id
        )
        if (resultSet.isFailure)
            return Result.failure(resultSet.exceptionOrNull()!!)
        if(flowApp == FlowApp.CHANGE){
            startProcessSendData()
        }
        return Result.success(true)
    }

}