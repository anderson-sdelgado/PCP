package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.presenter.visitterc.nome.NomeVisitTercModel
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc

interface GetNomeVisitTerc {
    suspend operator fun invoke(
        cpf: String,
        flowApp: FlowApp,
        id: Int
    ): Result<NomeVisitTercModel>
}

class IGetNomeVisitTerc(
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
                    cause = e
                )
            }
            val typeVisitTerc = resultTypeVisitTerc.getOrNull()!!
            val resultNomeVisitTerc = when (typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.getNome(cpf)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.getNome(cpf)
            }
            if (resultNomeVisitTerc.isFailure) {
                val e = resultNomeVisitTerc.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNomeVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val resultEmpresaVisitTerc = when (typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.getEmpresas(cpf)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.getEmpresas(cpf)
            }
            if (resultEmpresaVisitTerc.isFailure) {
                val e = resultEmpresaVisitTerc.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNomeVisitTerc",
                    message = e.message,
                    cause = e
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