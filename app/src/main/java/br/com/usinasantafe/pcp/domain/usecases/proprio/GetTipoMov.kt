package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import javax.inject.Inject

interface GetTipoMov {
    suspend operator fun invoke(): TypeMov
}

class GetTipoMovImpl @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
): GetTipoMov {

    override suspend fun invoke(): TypeMov {
        return movEquipProprioRepository.getTipoMovEquipProprio()
    }

}