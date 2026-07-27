package br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import javax.inject.Inject

interface CleanPassagVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanPassagVisitTerc @Inject constructor(
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository
): CleanPassagVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        val result = movEquipVisitTercPassagRepository.clean()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanPassagVisitTerc",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}