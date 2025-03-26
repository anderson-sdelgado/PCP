package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository

interface CleanEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanEquip(
    private val equipRepository: EquipRepository
): CleanEquip {

    override suspend fun invoke(): Result<Boolean> {
        val result = equipRepository.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanEquip",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}