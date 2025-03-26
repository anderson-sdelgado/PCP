package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository

interface CleanPassagColab {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanPassagColab(
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository
) : CleanPassagColab {

    override suspend fun invoke(): Result<Boolean> {
        val result = movEquipProprioPassagRepository.clear()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanPassagColab",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}