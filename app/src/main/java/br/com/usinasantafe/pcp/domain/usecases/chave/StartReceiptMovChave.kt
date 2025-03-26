package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.util.Date

interface StartReceiptMovChave {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class IStartReceiptMovChave(
    private val movChaveRepository: MovChaveRepository
): StartReceiptMovChave {

    override suspend fun invoke(id: Int): Result<Boolean> {
        try {
            val resultGet = movChaveRepository.get(id)
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartReceiptMovChave",
                    message = e.message,
                    cause = e
                )
            }
            val movChave = resultGet.getOrNull()!!
            movChave.observMovChave = null
            movChave.tipoMovChave = TypeMovKey.RECEIPT
            movChave.dthrMovChave = Date()
            movChave.statusForeignerMovChave = StatusForeigner.OUTSIDE
            val resultStart = movChaveRepository.start(movChave)
            if (resultStart.isFailure) {
                val e = resultStart.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartReceiptMovChave",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IStartReceiptMovChave",
                message = "-",
                cause = e
            )
        }
    }

}