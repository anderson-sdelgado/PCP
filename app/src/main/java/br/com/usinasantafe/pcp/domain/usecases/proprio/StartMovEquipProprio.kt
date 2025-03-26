package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.utils.TypeMovEquip

interface StartMovEquipProprio {
    suspend operator fun invoke(typeMov: TypeMovEquip): Result<Boolean>
}

class IStartMovEquipProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository
): StartMovEquipProprio {

    override suspend fun invoke(typeMov: TypeMovEquip): Result<Boolean> {
        try {
            val resultStart = movEquipProprioRepository.start(typeMov)
            if (resultStart.isFailure) {
                val e = resultStart.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartMovEquipProprio",
                    message = e.message,
                    cause = e
                )
            }
            val resultEquipSegClear = movEquipProprioEquipSegRepository.clear()
            if (resultEquipSegClear.isFailure) {
                val e = resultEquipSegClear.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartMovEquipProprio",
                    message = e.message,
                    cause = e
                )
            }
            val resultPassagClear = movEquipProprioPassagRepository.clear()
            if (resultPassagClear.isFailure) {
                val e = resultPassagClear.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartMovEquipProprio",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IStartMovEquipProprio",
                message = "-",
                cause = e
            )
        }
    }

}