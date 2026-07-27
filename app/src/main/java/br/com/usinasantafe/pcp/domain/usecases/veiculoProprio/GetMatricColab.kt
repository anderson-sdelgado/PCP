package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import javax.inject.Inject

interface GetMatricColab {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetMatricColab @Inject constructor(
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
                    cause = e.cause
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