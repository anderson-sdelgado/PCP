package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface GetDestinoVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetDestinoVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetDestinoVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        val result = movEquipVisitTercRepository.getDestino(id = id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetDestinoVisitTerc",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}