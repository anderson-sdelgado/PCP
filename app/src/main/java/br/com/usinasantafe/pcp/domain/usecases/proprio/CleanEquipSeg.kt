package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository

interface CleanEquipSeg {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanEquipSeg(
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository
): CleanEquipSeg {

    override suspend fun invoke(): Result<Boolean> {
        val result = movEquipProprioEquipSegRepository.clean()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanEquipSeg",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}