package br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.lib.FlowApp
import javax.inject.Inject

interface SetMotoristaResidencia {
    suspend operator fun invoke(
        motorista: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetMotoristaResidencia @Inject constructor(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val startProcessSendData: StartProcessSendData
) : SetMotoristaResidencia {

    override suspend fun invoke(
        motorista: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result = movEquipResidenciaRepository.setMotorista(
                motorista = motorista,
                flowApp = flowApp,
                id = id
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetMotoristaResidencia",
                    message = e.message,
                    cause = e.cause
                )
            }
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ISetMotoristaResidencia",
                message = "-",
                cause = e
            )
        }

    }

}