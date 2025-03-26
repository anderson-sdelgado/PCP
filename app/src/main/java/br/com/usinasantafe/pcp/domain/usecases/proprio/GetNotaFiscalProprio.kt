package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository

interface GetNotaFiscalProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<String?>
}

class IGetNotaFiscalProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : GetNotaFiscalProprio {

    override suspend fun invoke(
        id: Int
    ): Result<String?> {
        try {
            val resultNotaFiscal = movEquipProprioRepository.getNotaFiscal(id = id)
            if (resultNotaFiscal.isFailure) {
                val e = resultNotaFiscal.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNotaFiscalProprio",
                    message = e.message,
                    cause = e
                )
            }
            val notaFiscal = if(resultNotaFiscal.getOrNull() == null) null else resultNotaFiscal.getOrNull().toString()
            return Result.success(notaFiscal)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetNotaFiscalProprio",
                message = "-",
                cause = e
            )
        }
    }

}