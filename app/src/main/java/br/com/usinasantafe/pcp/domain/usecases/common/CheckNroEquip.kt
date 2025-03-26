package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository

interface CheckNroEquip {
    suspend operator fun invoke(nroEquip: String): Result<Boolean>
}

class ICheckNroEquip(
    private val equipRepository: EquipRepository
): CheckNroEquip {

    override suspend fun invoke(nroEquip: String): Result<Boolean> {
        try {
            val result = equipRepository.checkNro(nroEquip.toLong())
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckNroEquip",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckNroEquip",
                message = "-",
                cause = e
            )
        }
    }

}