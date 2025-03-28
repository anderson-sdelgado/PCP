package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository

interface SaveLocal {
    suspend operator fun invoke(list: List<Local>): Result<Boolean>
}

class ISaveLocal(
    private val localRepository: LocalRepository,
): SaveLocal {

    override suspend fun invoke(list: List<Local>): Result<Boolean> {
        val result = localRepository.addAll(list)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISaveLocal",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}