package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository

interface CleanColab {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanColab(
    private val colabRepository: ColabRepository
): CleanColab {

    override suspend fun invoke(): Result<Boolean> {
        val result = colabRepository.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanColab",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}