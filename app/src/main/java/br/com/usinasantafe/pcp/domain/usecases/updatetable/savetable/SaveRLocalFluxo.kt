package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.RLocalFluxoRepository

interface SaveRLocalFluxo {
    suspend operator fun invoke(list: List<RLocalFluxo>): Result<Boolean>
}

class ISaveRLocalFluxo(
    private val rLocalFluxoRepository: RLocalFluxoRepository,
): SaveRLocalFluxo {

    override suspend fun invoke(list: List<RLocalFluxo>): Result<Boolean> {
        val result = rLocalFluxoRepository.addAll(list)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISaveRLocalFluxo",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}