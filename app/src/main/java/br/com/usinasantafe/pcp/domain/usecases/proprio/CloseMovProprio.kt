package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository

interface CloseMovProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class ICloseMovProprio(
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
                cause = e
            )
        }
        return result
    }

}