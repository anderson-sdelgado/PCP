package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
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
            if (resultMatricColab.isFailure) {
                val e = resultMatricColab.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetMatricColab",
                    message = e.message,
                    cause = e
                )
            }
            val matricColab = resultMatricColab.getOrNull()!!
            return Result.success(matricColab.toString())
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetMatricColab",
                message = "-",
                cause = e
            )
        }
    }

}