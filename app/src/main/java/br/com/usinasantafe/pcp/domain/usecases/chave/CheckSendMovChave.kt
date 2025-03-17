package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository

interface CheckSendMovChave {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovChave(
    private val movChaveRepository: MovChaveRepository
): CheckSendMovChave {

    override suspend fun invoke(): Result<Boolean> {
        return movChaveRepository.checkSend()
    }

}