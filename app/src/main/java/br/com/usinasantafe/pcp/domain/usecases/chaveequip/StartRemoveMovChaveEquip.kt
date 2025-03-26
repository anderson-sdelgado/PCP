package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.util.Date

interface StartRemoveMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class IStartRemoveMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): StartRemoveMovChaveEquip {

    override suspend fun invoke(id: Int): Result<Boolean> {
        try {
            val resultGet = movChaveEquipRepository.get(id)
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartRemoveMovChaveEquip",
                    message = e.message,
                    cause = e
                )
            }
            val movChaveEquip = resultGet.getOrNull()!!
            movChaveEquip.observMovChaveEquip = null
            movChaveEquip.tipoMovChaveEquip = TypeMovKey.REMOVE
            movChaveEquip.dthrMovChaveEquip = Date()
            movChaveEquip.statusForeignerMovChaveEquip = StatusForeigner.OUTSIDE
            val resultStart = movChaveEquipRepository.start(movChaveEquip)
            if (resultStart.isFailure) {
                val e = resultStart.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartRemoveMovChaveEquip",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IStartRemoveMovChaveEquip",
                message = "-",
                cause = e
            )
        }
    }

}