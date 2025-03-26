package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetVeiculoResidencia {
    suspend operator fun invoke(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetVeiculoResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val startProcessSendData: StartProcessSendData
) : SetVeiculoResidencia {

    override suspend fun invoke(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result = movEquipResidenciaRepository.setVeiculo(
                veiculo = veiculo,
                flowApp = flowApp,
                id = id
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetVeiculoResidencia",
                    message = e.message,
                    cause = e
                )
            }
            if (flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetVeiculoResidencia",
                message = "-",
                cause = e
            )
        }

    }

}