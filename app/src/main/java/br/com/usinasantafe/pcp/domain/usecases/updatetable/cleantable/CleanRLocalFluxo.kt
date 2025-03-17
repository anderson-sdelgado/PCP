package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.RLocalFluxoRepository

interface CleanRLocalFluxo {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanRLocalFluxo(
    private val rLocalFluxoRepository: RLocalFluxoRepository
): CleanRLocalFluxo {

    override suspend fun invoke(): Result<Boolean> {
        return rLocalFluxoRepository.deleteAll()
    }

}