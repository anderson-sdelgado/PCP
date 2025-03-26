package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetPlacaVisitTerc {
    suspend operator fun invoke(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetPlacaVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val startProcessSendData: StartProcessSendData
) : SetPlacaVisitTerc {

    override suspend fun invoke(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result = movEquipVisitTercRepository.setPlaca(
                placa = placa,
                flowApp = flowApp,
                id = id
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetPlacaVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ISetPlacaVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}