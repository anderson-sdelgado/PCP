package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository

interface GetDestinoProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<String>
}

class IGetDestinoProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : GetDestinoProprio {

    override suspend fun invoke(
        id: Int
    ): Result<String> {
        return movEquipProprioRepository.getDestino(id = id)
    }

}