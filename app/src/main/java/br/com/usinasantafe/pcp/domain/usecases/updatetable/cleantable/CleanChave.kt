package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository

interface CleanChave {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanChave(
    private val chaveRepository: ChaveRepository
): CleanChave {

    override suspend fun invoke(): Result<Boolean> {
        val result = chaveRepository.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanChave",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}