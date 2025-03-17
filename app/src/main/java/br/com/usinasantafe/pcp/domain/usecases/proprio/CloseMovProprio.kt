package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
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
        return movEquipProprioRepository.setClose(id)
    }

}