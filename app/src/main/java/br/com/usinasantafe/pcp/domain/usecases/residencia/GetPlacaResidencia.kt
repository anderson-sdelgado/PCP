package br.com.usinasantafe.pcp.domain.usecases.residencia

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
        return movEquipResidenciaRepository.getPlaca(id = id)
    }

}