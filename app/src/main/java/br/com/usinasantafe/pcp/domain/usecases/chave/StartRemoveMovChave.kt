package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository

interface StartRemoveMovChave {
    suspend operator fun invoke(): Result<Boolean>
}

class IStartRemoveMovChave(
    private val movChaveRepository: MovChaveRepository
): StartRemoveMovChave {

    override suspend fun invoke(): Result<Boolean> {
        return movChaveRepository.start()
    }

}