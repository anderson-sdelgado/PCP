package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.FluxoRepository

interface CleanFluxo {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanFluxo(
    private val fluxoRepository: FluxoRepository
): CleanFluxo {

    override suspend fun invoke(): Result<Boolean> {
        val result = fluxoRepository.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanFluxo",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}