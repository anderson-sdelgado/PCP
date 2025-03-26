package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository

interface CloseAllMovChave {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovChave(
    private val movChaveRepository: MovChaveRepository
): CloseAllMovChave {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultList = movChaveRepository.listOpen()
            if (resultList.isFailure){
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "ICloseAllMovChave",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = resultList.getOrNull()!!
            for(entity in entityList){
                val resulClose = movChaveRepository.setClose(entity.idMovChave!!)
                if (resulClose.isFailure){
                    val e = resulClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ICloseAllMovChave",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICloseAllMovChave",
                message = "-",
                cause = e
            )
        }
    }

}