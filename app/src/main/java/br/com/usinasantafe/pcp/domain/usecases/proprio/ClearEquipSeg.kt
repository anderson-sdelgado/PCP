package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioSegRepository
import javax.inject.Inject

interface ClearEquipSeg {
    suspend operator fun invoke(): Boolean
}

class ClearEquipSegImpl @Inject constructor(
    private val movEquipProprioSegRepository: MovEquipProprioSegRepository,
): ClearEquipSeg {

    override suspend fun invoke(): Boolean {
        return try {
            movEquipProprioSegRepository.clearEquipSeg()
        } catch (exception: Exception) {
            false
        }
    }

}