package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import javax.inject.Inject

interface CheckSendMovProprio {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMovProprio @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository
): CheckSendMovProprio {

    override suspend fun invoke(): Result<Boolean> {
        val result = movEquipProprioRepository.checkSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICheckSendMovProprio",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}