package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository

interface StartInputMovEquipVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class IStartInputMovEquipVisitTerc(
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
                    cause = e
                )
            }
            val resultClear = movEquipVisitTercPassagRepository.clean()
            if (resultClear.isFailure) {
                val e = resultClear.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartInputMovEquipVisitTerc",
                    message = e.message,
                    cause = e
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