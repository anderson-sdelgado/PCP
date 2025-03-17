package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository

interface CleanChave {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanChave(
    private val chaveRepository: ChaveRepository
): CleanChave {

    override suspend fun invoke(): Result<Boolean> {
        return chaveRepository.deleteAll()
    }

}