package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import javax.inject.Inject

interface GetDestinoProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetDestinoProprio @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : GetDestinoProprio {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        val result = movEquipProprioRepository.getDestino(id = id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetDestinoProprio",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}