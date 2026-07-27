package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import javax.inject.Inject

interface GetNomeColab {
    suspend operator fun invoke(matric: String): Result<String>
}

class IGetNomeColab @Inject constructor(
    private val colabRepository: ColabRepository,
) : GetNomeColab {

    override suspend fun invoke(matric: String): Result<String> {
        try {
            val result = colabRepository.getNome(
                matric.toInt()
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNomeColab",
                    message = e.message,
                    cause = e.cause
                )
            }
            if(result.getOrNull() == null){
                return resultFailure(
                    context = "IGetNomeColab",
                    message = "-",
                    cause = NullPointerException()
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