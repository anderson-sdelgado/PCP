package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc

interface CheckCpfVisitTerc {
    suspend operator fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ICheckCpfVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository
) : CheckCpfVisitTerc {

    override suspend fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultTypeVisitTerc = movEquipVisitTercRepository.getTypeVisitTerc(flowApp, id)
            if (resultTypeVisitTerc.isFailure) {
                val e = resultTypeVisitTerc.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckCpfVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val typeVisitTerc = resultTypeVisitTerc.getOrNull()!!
            val resultCheck = when (typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.checkCPF(cpf)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.checkCPF(cpf)
            }
            if (resultCheck.isFailure) {
                val e = resultCheck.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckCpfVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val result = resultCheck.getOrNull()!!
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckCpfVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}