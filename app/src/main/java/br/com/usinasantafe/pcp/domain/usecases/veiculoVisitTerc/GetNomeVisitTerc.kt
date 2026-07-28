package br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.nome.NomeVisitTercModel
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import javax.inject.Inject

interface GetNomeVisitTerc {
    suspend operator fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<NomeVisitTercModel>
}

class IGetNomeVisitTerc @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository
) : GetNomeVisitTerc {

    override suspend fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<NomeVisitTercModel> {
        try {
            val resultTypeVisitTerc = movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = flowApp,
                id = id
            )
            if (resultTypeVisitTerc.isFailure) {
                val e = resultTypeVisitTerc.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNomeVisitTerc",
                    message = e.message,
                    cause = e.cause
                )
            }
            val typeVisitTerc = resultTypeVisitTerc.getOrNull()!!
            val resultNomeVisitTerc = when (typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.getNomeByCpf(cpf)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.getNomeByCpf(cpf)
            }
            if (resultNomeVisitTerc.isFailure) {
                val e = resultNomeVisitTerc.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNomeVisitTerc",
                    message = e.message,
                    cause = e.cause
                )
            }
            val resultEmpresaVisitTerc = when (typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.getEmpresasByCpf(cpf)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.getEmpresasByCpf(cpf)
            }
            if (resultEmpresaVisitTerc.isFailure) {
                val e = resultEmpresaVisitTerc.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNomeVisitTerc",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(
                NomeVisitTercModel(
                    tipo = typeVisitTerc.name,
                    nome = resultNomeVisitTerc.getOrNull()!!,
                    empresa = resultEmpresaVisitTerc.getOrNull()!!
                )
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetNomeVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}