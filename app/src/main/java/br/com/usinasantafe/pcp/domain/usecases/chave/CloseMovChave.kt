package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository

interface CloseMovChave {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class ICloseMovChave(
    private val movChaveRepository: MovChaveRepository
): CloseMovChave {

    override suspend fun invoke(id: Int): Result<Boolean> {
        return movChaveRepository.setClose(id)
    }

}