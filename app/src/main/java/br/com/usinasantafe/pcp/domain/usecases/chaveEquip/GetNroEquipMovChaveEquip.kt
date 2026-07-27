package br.com.usinasantafe.pcp.domain.usecases.chaveEquip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import javax.inject.Inject

interface GetNroEquipMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetNroEquipMovChaveEquip @Inject constructor(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val equipRepository: EquipRepository
): GetNroEquipMovChaveEquip {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultIdEquip = movChaveEquipRepository.getIdEquip(id = id)
            if (resultIdEquip.isFailure) {
                val e = resultIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNroEquipMovChaveEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idEquip = resultIdEquip.getOrNull()!!
            val resultEquip = equipRepository.getNro(idEquip = idEquip)
            if (resultEquip.isFailure) {
                val e = resultEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNroEquipMovChaveEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(resultEquip.getOrNull()!!.toString())
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetNroEquipMovChaveEquip",
                message = "-",
                cause = e
            )
        }
    }

}