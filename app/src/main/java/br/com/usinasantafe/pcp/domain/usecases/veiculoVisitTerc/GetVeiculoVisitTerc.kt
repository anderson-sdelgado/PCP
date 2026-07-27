package br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface GetVeiculoVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetVeiculoVisitTerc @Inject constructor(
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
                cause = e.cause
            )
        }
        return result
    }

}