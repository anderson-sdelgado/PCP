package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalTrabRepository

interface CleanLocalTrab {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanLocalTrab(
    private val localTrabRepository: LocalTrabRepository
): CleanLocalTrab {

    override suspend fun invoke(): Result<Boolean> {
        val result = localTrabRepository.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanLocalTrab",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}