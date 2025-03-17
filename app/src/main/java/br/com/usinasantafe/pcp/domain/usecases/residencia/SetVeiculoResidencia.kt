package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
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
            val resultSet = movEquipResidenciaRepository.setVeiculo(
                veiculo = veiculo,
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure)
                return Result.failure(resultSet.exceptionOrNull()!!)
            if (flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SetVeiculoResidenciaImpl",
                    cause = e.cause
                )
            )
        }

    }

}