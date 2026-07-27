package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import javax.inject.Inject

interface CleanEquipSeg {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanEquipSeg @Inject constructor(
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository
): CleanEquipSeg {

    override suspend fun invoke(): Result<Boolean> {
        val result = movEquipProprioEquipSegRepository.clean()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanEquipSeg",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}