package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository

interface CleanLocal {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanLocal(
    private val localRepository: LocalRepository
): CleanLocal {

    override suspend fun invoke(): Result<Boolean> {
        return localRepository.deleteAll()
    }

}