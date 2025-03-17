package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository

interface SaveVisitante {
    suspend operator fun invoke(list: List<Visitante>): Result<Boolean>
}

class ISaveVisitante(
    private val colabRepository: VisitanteRepository,
): SaveVisitante {

    override suspend fun invoke(list: List<Visitante>): Result<Boolean> {
        return colabRepository.addAll(list)
    }

}