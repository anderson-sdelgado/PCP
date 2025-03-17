package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface CloseMovResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class ICloseMovResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : CloseMovResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        return movEquipResidenciaRepository.setClose(id)
    }

}