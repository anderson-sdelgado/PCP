package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.FluxoRepository

interface SaveFluxo {
    suspend operator fun invoke(list: List<Fluxo>): Result<Boolean>
}

class ISaveFluxo(
    private val fluxoRepository: FluxoRepository
): SaveFluxo {

    override suspend fun invoke(list: List<Fluxo>): Result<Boolean> {
        val result = fluxoRepository.addAll(list)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISaveFluxo",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}