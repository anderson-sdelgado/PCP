package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetVeiculoVisitTerc {
    suspend operator fun invoke(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetVeiculoVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val startProcessSendData: StartProcessSendData
) : SetVeiculoVisitTerc {

    override suspend fun invoke(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        val result = movEquipVisitTercRepository.setVeiculo(
            veiculo = veiculo,
            flowApp = flowApp,
            id = id
        )
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetVeiculoVisitTerc",
                message = e.message,
                cause = e
            )
        }
        if(flowApp == FlowApp.CHANGE)
            startProcessSendData()
        return result
    }

}