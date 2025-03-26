package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc

interface GetCpfVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetCpfVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository
): GetCpfVisitTerc {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultGetType = movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.CHANGE,
                id = id
            )
            if (resultGetType.isFailure) {
                val e = resultGetType.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetCpfVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val typeVisitTerc = resultGetType.getOrNull()!!
            val resultGetIdVisitTerc = movEquipVisitTercRepository.getIdVisitTerc(id)
            if (resultGetIdVisitTerc.isFailure) {
                val e = resultGetIdVisitTerc.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetCpfVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val idVisitTerc = resultGetIdVisitTerc.getOrNull()!!
            val resultGetCpf = when(typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.getCpf(idVisitTerc)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.getCpf(idVisitTerc)
            }
            if (resultGetCpf.isFailure) {
                val e = resultGetCpf.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetCpfVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultGetCpf.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetCpfVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}