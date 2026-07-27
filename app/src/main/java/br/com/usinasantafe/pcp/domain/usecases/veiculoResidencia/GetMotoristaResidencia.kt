package br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import javax.inject.Inject

interface GetMotoristaResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetMotoristaResidencia @Inject constructor(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : GetMotoristaResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        val result = movEquipResidenciaRepository.getMotorista(id = id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetMotoristaResidencia",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}