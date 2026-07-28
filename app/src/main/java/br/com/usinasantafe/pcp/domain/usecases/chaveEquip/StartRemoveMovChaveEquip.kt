package br.com.usinasantafe.pcp.domain.usecases.chaveEquip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.TypeMovKey
import java.util.Date
import javax.inject.Inject

interface StartRemoveMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class IStartRemoveMovChaveEquip @Inject constructor(
    private val movChaveEquipRepository: MovChaveEquipRepository
): StartRemoveMovChaveEquip {

    override suspend fun invoke(id: Int): Result<Boolean> {
        try {
            val resultGet = movChaveEquipRepository.getById(id)
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartRemoveMovChaveEquip",
                    message = e.message,
                    cause = e.cause
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
                    cause = e.cause
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