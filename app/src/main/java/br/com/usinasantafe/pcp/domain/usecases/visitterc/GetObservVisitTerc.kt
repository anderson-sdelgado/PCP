package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface GetObservVisitTerc {
    suspend operator fun invoke(
        id: Int,
    ): Result<String?>
}

class IGetObservVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetObservVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String?> {
        return movEquipVisitTercRepository.getObserv(id = id)
    }

}