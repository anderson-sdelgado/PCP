package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import javax.inject.Inject

interface GetObservMovChave {
    suspend operator fun invoke(id: Int): Result<String?>
}

class IGetObservMovChave @Inject constructor(
    private val movChaveRepository: MovChaveRepository
): GetObservMovChave {

    override suspend fun invoke(id: Int): Result<String?> {
        val result = movChaveRepository.getObservById(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetObservMovChave",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}