package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface GetVeiculoResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetVeiculoResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : GetVeiculoResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        val result = movEquipResidenciaRepository.getVeiculo(id = id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetVeiculoResidencia",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}