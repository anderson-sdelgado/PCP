package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository

interface CleanLocal {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanLocal(
    private val localRepository: LocalRepository
): CleanLocal {

    override suspend fun invoke(): Result<Boolean> {
        val result = localRepository.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanLocal",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}