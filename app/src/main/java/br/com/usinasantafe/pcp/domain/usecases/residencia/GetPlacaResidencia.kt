package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface GetPlacaResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetPlacaResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : GetPlacaResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        val result = movEquipResidenciaRepository.getPlaca(id = id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetPlacaResidencia",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}