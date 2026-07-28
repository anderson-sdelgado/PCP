package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import javax.inject.Inject

interface CheckMatricColab {
    suspend operator fun invoke(matricColab: String): Result<Boolean>
}

class ICheckMatricColab @Inject constructor(
    private val colabRepository: ColabRepository
): CheckMatricColab {

    override suspend fun invoke(matricColab: String): Result<Boolean> {
        try {
            val matric = matricColab.toInt()
            val result = colabRepository.hasMatric(matric)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckMatricColab",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckMatricColab",
                message = "-",
                cause = e
            )
        }
    }

}
