package br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface GetObservVisitTerc {
    suspend operator fun invoke(
        id: Int,
    ): Result<String?>
}

class IGetObservVisitTerc @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetObservVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String?> {
        val result = movEquipVisitTercRepository.getObserv(id = id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetObservVisitTerc",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}