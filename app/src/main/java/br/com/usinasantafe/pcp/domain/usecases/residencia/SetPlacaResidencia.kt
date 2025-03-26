package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetPlacaResidencia {
    suspend operator fun invoke(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetPlacaResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val startProcessSendData: StartProcessSendData
) : SetPlacaResidencia {

    override suspend fun invoke(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result = movEquipResidenciaRepository.setPlaca(
                placa = placa,
                flowApp = flowApp,
                id = id
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetPlacaResidencia",
                    message = e.message,
                    cause = e
                )
            }
            if (flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetPlacaResidencia",
                message = "-",
                cause = e
            )
        }

    }

}