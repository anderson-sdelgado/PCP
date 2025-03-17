package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
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
            val resultSet = movEquipProprioRepository.setNotaFiscal(
                notaFiscal = if(notaFiscal.isNullOrEmpty()) null else notaFiscal.toInt(),
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure)
                return Result.failure(resultSet.exceptionOrNull()!!)
            if(flowApp == FlowApp.CHANGE){
                startProcessSendData()
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SetNotaFiscalProprio",
                    cause = e
                )
            )
        }
    }

}