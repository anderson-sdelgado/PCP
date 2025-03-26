package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetDestinoVisitTerc {
    suspend operator fun invoke(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetDestinoVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val startProcessSendData: StartProcessSendData
) : SetDestinoVisitTerc {

    override suspend fun invoke(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        val result = movEquipVisitTercRepository.setDestino(
            destino = destino,
            flowApp = flowApp,
            id = id
        )
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetDestinoVisitTerc",
                message = e.message,
                cause = e
            )
        }
        if(flowApp == FlowApp.CHANGE)
            startProcessSendData()
        return result
    }

}