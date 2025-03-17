package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository

interface SetStatusSentMovChave {
    suspend operator fun invoke(list: List<MovChave>): Result<Boolean>
}

class ISetStatusSentMovChave(
    private val movChaveRepository: MovChaveRepository
): SetStatusSentMovChave {

    override suspend fun invoke(list: List<MovChave>): Result<Boolean> {
        return movChaveRepository.setSent(list)
    }

}