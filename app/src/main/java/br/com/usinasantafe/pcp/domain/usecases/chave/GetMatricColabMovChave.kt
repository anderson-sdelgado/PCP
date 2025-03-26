package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository

interface GetMatricColabMovChave {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetMatricColabMovChave(
    private val movChaveRepository: MovChaveRepository
): GetMatricColabMovChave {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultGetMatricColab = movChaveRepository.getMatricColab(id)
            if (resultGetMatricColab.isFailure) {
                val e = resultGetMatricColab.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetMatricColabMovChave",
                    message = e.message,
                    cause = e
                )
            }
            val matricColab = resultGetMatricColab.getOrNull()!!
            return Result.success(matricColab.toString())
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetMatricColabMovChave",
                message = "-",
                cause = e
            )
        }
    }

}