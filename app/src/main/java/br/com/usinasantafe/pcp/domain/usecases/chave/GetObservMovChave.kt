package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository

interface GetObservMovChave {
    suspend operator fun invoke(id: Int): Result<String?>
}

class IGetObservMovChave(
    private val movChaveRepository: MovChaveRepository
): GetObservMovChave {

    override suspend fun invoke(id: Int): Result<String?> {
        return movChaveRepository.getObserv(id)
    }

}