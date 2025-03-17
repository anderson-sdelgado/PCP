package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.repositories.stable.FluxoRepository

interface SaveFluxo {
    suspend operator fun invoke(list: List<Fluxo>): Result<Boolean>
}

class ISaveFluxo(
    private val fluxoRepository: FluxoRepository
): SaveFluxo {

    override suspend fun invoke(list: List<Fluxo>): Result<Boolean> {
        return fluxoRepository.addAll(list)
    }

}