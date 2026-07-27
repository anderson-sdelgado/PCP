package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import javax.inject.Inject

interface CleanPassagColab {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanPassagColab @Inject constructor(
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository
) : CleanPassagColab {

    override suspend fun invoke(): Result<Boolean> {
        val result = movEquipProprioPassagRepository.clean()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanPassagColab",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}