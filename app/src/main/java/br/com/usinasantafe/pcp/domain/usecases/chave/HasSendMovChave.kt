package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

interface CheckSendMovChave {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovChave @Inject constructor(
    private val movChaveRepository: MovChaveRepository
): CheckSendMovChave {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            movChaveRepository.hasSend().getOrThrow()
        }

}