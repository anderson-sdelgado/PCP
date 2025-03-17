package br.com.usinasantafe.pcp.domain.usecases.residencia

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
        return movEquipResidenciaRepository.getMotorista(id = id)
    }

}