package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository

interface CheckSendMovChave {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovChave(
    private val movChaveRepository: MovChaveRepository
): CheckSendMovChave {

    override suspend fun invoke(): Result<Boolean> {
        val result = movChaveRepository.checkSend()
        if (result.isFailure){
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICheckSendMovChave",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}