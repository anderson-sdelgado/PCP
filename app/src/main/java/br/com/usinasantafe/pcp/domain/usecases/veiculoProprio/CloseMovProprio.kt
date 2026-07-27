package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import javax.inject.Inject

interface CloseMovProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class ICloseMovProprio @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
): CloseMovProprio {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        val result = movEquipProprioRepository.setClose(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICloseMovProprio",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}