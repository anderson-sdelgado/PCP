package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository

interface GetMatricColab {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetMatricColab(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : GetMatricColab {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultMatricColab = movEquipProprioRepository.getMatricColab(id = id)
            if (resultMatricColab.isFailure)
                return Result.failure(resultMatricColab.exceptionOrNull()!!)
            val matricColab = resultMatricColab.getOrNull()!!
            return Result.success(matricColab.toString())
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetMatricColabImpl",
                    cause = e
                )
            )
        }
    }

}