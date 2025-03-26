package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository

interface GetMotoristaResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetMotoristaResidencia(
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
                cause = e
            )
        }
        return result
    }

}