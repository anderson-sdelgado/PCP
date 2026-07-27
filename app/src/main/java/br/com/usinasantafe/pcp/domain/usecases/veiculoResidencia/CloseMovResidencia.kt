package br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import javax.inject.Inject

interface CloseMovResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class ICloseMovResidencia @Inject constructor(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : CloseMovResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        val result = movEquipResidenciaRepository.setClose(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICloseMovResidencia",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}