package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.utils.TypeMovEquip

interface GetTypeMov {
    suspend operator fun invoke(): Result<TypeMovEquip>
}

class IGetTypeMov(
    private val movEquipProprioRepository: MovEquipProprioRepository,
) : GetTypeMov {

    override suspend fun invoke(): Result<TypeMovEquip> {
        val result = movEquipProprioRepository.getTipoMov()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetObservProprio",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}