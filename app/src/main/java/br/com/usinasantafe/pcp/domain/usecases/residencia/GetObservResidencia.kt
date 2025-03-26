package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
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
        val result = movEquipResidenciaRepository.getObserv(id = id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetObservResidencia",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}