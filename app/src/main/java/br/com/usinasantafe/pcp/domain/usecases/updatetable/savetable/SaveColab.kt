package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository

interface SaveColab {
    suspend operator fun invoke(list: List<Colab>): Result<Boolean>
}

class ISaveColab(
    private val colabRepository: ColabRepository,
): SaveColab {

    override suspend fun invoke(list: List<Colab>): Result<Boolean> {
        val result = colabRepository.addAll(list)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISaveColab",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}