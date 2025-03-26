package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.utils.TypeVisitTerc

interface GetMotoristaVisitTerc {
    suspend operator fun invoke(
        typeVisitTerc: TypeVisitTerc,
        idVisitTerc: Int
    ): Result<String>
}

class IGetMotoristaVisitTerc(
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository
): GetMotoristaVisitTerc {

    override suspend fun invoke(
        typeVisitTerc: TypeVisitTerc,
        idVisitTerc: Int
    ): Result<String> {
        when (typeVisitTerc) {
            TypeVisitTerc.VISITANTE -> {
                val resultGetVisit =
                    visitanteRepository.get(idVisitTerc)
                if (resultGetVisit.isFailure) {
                    val e = resultGetVisit.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMotoristaVisitTerc",
                        message = e.message,
                        cause = e
                    )
                }
                val visitante = resultGetVisit.getOrNull()!!
                return Result.success("${visitante.cpfVisitante} - ${visitante.nomeVisitante}")
            }

            TypeVisitTerc.TERCEIRO -> {
                val resultGetTerc =
                    terceiroRepository.get(idVisitTerc)
                if (resultGetTerc.isFailure) {
                    val e = resultGetTerc.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMotoristaVisitTerc",
                        message = e.message,
                        cause = e
                    )
                }
                val terceiro = resultGetTerc.getOrNull()!!
                return Result.success("${terceiro.cpfTerceiro} - ${terceiro.nomeTerceiro}")
            }
        }
    }

}