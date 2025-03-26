package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.RLocalFluxoRepository

interface CleanRLocalFluxo {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanRLocalFluxo(
    private val rLocalFluxoRepository: RLocalFluxoRepository
): CleanRLocalFluxo {

    override suspend fun invoke(): Result<Boolean> {
        val result = rLocalFluxoRepository.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanRLocalFluxo",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}