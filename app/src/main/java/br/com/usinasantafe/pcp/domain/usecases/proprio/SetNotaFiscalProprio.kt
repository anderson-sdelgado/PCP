package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp

interface SetNotaFiscalProprio {
    suspend operator fun invoke(
        notaFiscal: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetNotaFiscalProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val startProcessSendData: StartProcessSendData
) : SetNotaFiscalProprio {

    override suspend fun invoke(
        notaFiscal: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result = movEquipProprioRepository.setNotaFiscal(
                notaFiscal = if(notaFiscal.isNullOrEmpty()) null else notaFiscal.toInt(),
                flowApp = flowApp,
                id = id
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetNotaFiscalProprio",
                    message = e.message,
                    cause = e
                )
            }
            if(flowApp == FlowApp.CHANGE){
                startProcessSendData()
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetNotaFiscalProprio",
                message = "-",
                cause = e
            )
        }
    }

}