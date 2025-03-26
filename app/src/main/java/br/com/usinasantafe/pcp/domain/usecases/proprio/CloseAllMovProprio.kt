package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository

interface CloseAllMovProprio {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository,
) : CloseAllMovProprio {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultProprioList = movEquipProprioRepository.listOpen()
            if (resultProprioList.isFailure) {
                val e = resultProprioList.exceptionOrNull()!!
                return resultFailure(
                    context = "ICloseAllMovProprio",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = resultProprioList.getOrNull()!!
            for(entity in entityList){
                val resultClose = movEquipProprioRepository.setClose(entity.idMovEquipProprio!!)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ICloseAllMovProprio",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICloseAllMovProprio",
                message = "-",
                cause = e
            )
        }
    }

}