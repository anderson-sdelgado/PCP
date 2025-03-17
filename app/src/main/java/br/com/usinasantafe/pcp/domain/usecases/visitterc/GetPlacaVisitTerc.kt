package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface GetPlacaVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetPlacaVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetPlacaVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        return movEquipVisitTercRepository.getPlaca(id = id)
    }

}