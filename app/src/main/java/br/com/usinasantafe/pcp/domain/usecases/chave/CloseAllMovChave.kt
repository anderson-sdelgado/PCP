package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.required
import javax.inject.Inject

interface CloseAllMovChave {
    suspend operator fun invoke(): EmptyResult
}

class ICloseAllMovChave @Inject constructor(
    private val movChaveRepository: MovChaveRepository
): CloseAllMovChave {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            val entityList = movChaveRepository.listOpen().getOrThrow()
            for (entity in entityList) {
                val id = entity::idMovChave.required()
                movChaveRepository.setClose(id).getOrThrow()
            }
        }

}