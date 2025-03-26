package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository

interface GetNroEquipProprio {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetNroEquipProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val equipRepository: EquipRepository
): GetNroEquipProprio {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultIdEquip = movEquipProprioRepository.getIdEquip(id = id)
            if (resultIdEquip.isFailure) {
                val e = resultIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNroEquipProprio",
                    message = e.message,
                    cause = e
                )
            }
            val idEquip = resultIdEquip.getOrNull()!!
            val resultEquip = equipRepository.getNro(idEquip = idEquip)
            if (resultEquip.isFailure) {
                val e = resultEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNroEquipProprio",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultEquip.getOrNull()!!.toString())
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetNroEquipProprio",
                message = "-",
                cause = e
            )
        }
    }

}