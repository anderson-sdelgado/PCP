package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface GetVeiculoVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetVeiculoVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
) : GetVeiculoVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        return movEquipVisitTercRepository.getVeiculo(id = id)
    }

}