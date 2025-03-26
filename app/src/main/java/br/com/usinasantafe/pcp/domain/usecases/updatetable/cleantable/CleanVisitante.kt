package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository

interface CleanVisitante {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanVisitante(
    private val visitanteRepository: VisitanteRepository
): CleanVisitante {

    override suspend fun invoke(): Result<Boolean> {
        val result = visitanteRepository.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanVisitante",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}