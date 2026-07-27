package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import javax.inject.Inject

interface CloseMovChave {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class ICloseMovChave @Inject constructor(
    private val movChaveRepository: MovChaveRepository
): CloseMovChave {

    override suspend fun invoke(id: Int): Result<Boolean> {
        val result = movChaveRepository.setClose(id)
        if (result.isFailure){
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICloseMovChave",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}