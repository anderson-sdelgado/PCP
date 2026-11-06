package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

interface CloseMovChave {
    suspend operator fun invoke(id: Int): EmptyResult
}

class ICloseMovChave @Inject constructor(
    private val movChaveRepository: MovChaveRepository
): CloseMovChave {

    override suspend fun invoke(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movChaveRepository.setClose(id).getOrThrow()
        }

}