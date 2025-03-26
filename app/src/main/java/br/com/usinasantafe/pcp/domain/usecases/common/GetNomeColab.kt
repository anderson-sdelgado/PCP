package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository

interface GetNomeColab {
    suspend operator fun invoke(matric: String): Result<String>
}

class IGetNomeColab(
    private val colabRepository: ColabRepository,
) : GetNomeColab {

    override suspend fun invoke(matric: String): Result<String> {
        try {
            val result = colabRepository.getNome(matric.toInt())
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNomeColab",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetNomeColab",
                message = "-",
                cause = e
            )
        }
    }

}