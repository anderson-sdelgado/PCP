package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalTrabRepository

interface SaveLocalTrab {
    suspend operator fun invoke(list: List<LocalTrab>): Result<Boolean>
}

class ISaveLocalTrab(
    private val localTrabRepository: LocalTrabRepository
): SaveLocalTrab {

    override suspend fun invoke(list: List<LocalTrab>): Result<Boolean> {
        val result = localTrabRepository.addAll(list)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISaveLocalTrab",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}