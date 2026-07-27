package br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface GetPlacaVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetPlacaVisitTerc @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetPlacaVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        val result = movEquipVisitTercRepository.getPlaca(id = id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetPlacaVisitTerc",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}