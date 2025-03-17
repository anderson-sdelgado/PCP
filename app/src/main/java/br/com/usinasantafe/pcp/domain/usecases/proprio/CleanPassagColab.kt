package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository

interface CleanPassagColab {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanPassagColab(
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository
) : CleanPassagColab {

    override suspend fun invoke(): Result<Boolean> {
        return movEquipProprioPassagRepository.clear()
    }

}