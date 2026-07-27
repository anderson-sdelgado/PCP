package br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.lib.FlowApp
import javax.inject.Inject

interface GetTitleCpfVisitTerc {
    suspend operator fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<String>
}

class IGetTitleCpfVisitTerc @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetTitleCpfVisitTerc {

    override suspend fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<String> {
        try {
            val result = movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = flowApp,
                id = id
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetTitleCpfVisitTerc",
                    message = e.message,
                    cause = e.cause
                )
            }
            val typeVisitTerc = result.getOrNull()!!.name
            return Result.success(typeVisitTerc)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetTitleCpfVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}