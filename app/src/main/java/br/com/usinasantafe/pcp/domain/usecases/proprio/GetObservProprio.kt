package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository

interface GetObservProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<String?>
}

class IGetObservProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : GetObservProprio {

    override suspend fun invoke(
        id: Int
    ): Result<String?> {
        val result = movEquipProprioRepository.getObserv(id = id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetObservProprio",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}