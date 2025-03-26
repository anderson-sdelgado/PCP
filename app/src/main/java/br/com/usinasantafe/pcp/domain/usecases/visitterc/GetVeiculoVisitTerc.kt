package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
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
        val result = movEquipVisitTercRepository.getVeiculo(id = id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetVeiculoVisitTerc",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}