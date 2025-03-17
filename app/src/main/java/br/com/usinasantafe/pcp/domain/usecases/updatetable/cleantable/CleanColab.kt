package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository

interface CleanColab {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanColab(
    private val colabRepository: ColabRepository
): CleanColab {

    override suspend fun invoke(): Result<Boolean> {
        return colabRepository.deleteAll()
    }

}