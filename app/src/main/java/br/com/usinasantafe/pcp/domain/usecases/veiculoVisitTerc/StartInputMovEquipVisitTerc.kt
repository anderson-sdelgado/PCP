package br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface StartInputMovEquipVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class IStartInputMovEquipVisitTerc @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
) : StartInputMovEquipVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultStart = movEquipVisitTercRepository.start()
            if (resultStart.isFailure) {
                val e = resultStart.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartInputMovEquipVisitTerc",
                    message = e.message,
                    cause = e.cause
                )
            }
            val resultClear = movEquipVisitTercPassagRepository.clean()
            if (resultClear.isFailure) {
                val e = resultClear.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartInputMovEquipVisitTerc",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IStartInputMovEquipVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}