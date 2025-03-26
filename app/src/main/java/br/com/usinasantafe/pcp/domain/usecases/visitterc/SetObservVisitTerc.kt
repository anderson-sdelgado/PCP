package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetObservVisitTerc {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetObservVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val startProcessSendData: StartProcessSendData
) : SetObservVisitTerc {

    override suspend fun invoke(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result = movEquipVisitTercRepository.setObserv(
                observ = observ,
                flowApp = flowApp,
                id = id
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetObservVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetObservVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}