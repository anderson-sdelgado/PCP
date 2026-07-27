package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.lib.FlowApp
import javax.inject.Inject

interface SetDestinoProprio {
    suspend operator fun invoke(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetDestinoProprio @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val startProcessSendData: StartProcessSendData
): SetDestinoProprio {

    override suspend fun invoke(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        val result = movEquipProprioRepository.setDestino(
            destino = destino,
            flowApp = flowApp,
            id = id
        )
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetDestinoProprio",
                message = e.message,
                cause = e.cause
            )
        }
        if(flowApp == FlowApp.CHANGE)
            startProcessSendData()
        return result
    }

}