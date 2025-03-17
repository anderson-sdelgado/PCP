package br.com.usinasantafe.pcp.domain.usecases.visitterc

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
        return movEquipVisitTercRepository.getDestino(id = id)
    }

}