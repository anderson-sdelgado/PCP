package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface GetObservResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String?>
}

class IGetObservResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : GetObservResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String?> {
        return movEquipResidenciaRepository.getObserv(id = id)
    }

}