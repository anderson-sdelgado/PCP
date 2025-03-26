package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository

interface CloseAllMovChaveEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): CloseAllMovChaveEquip {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultList = movChaveEquipRepository.listOpen()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "ICloseAllMovChaveEquip",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = resultList.getOrNull()!!
            for(entity in entityList){
                val resulClose = movChaveEquipRepository.setClose(entity.idMovChaveEquip!!)
                if (resulClose.isFailure) {
                    val e = resulClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ICloseAllMovChaveEquip",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICloseAllMovChaveEquip",
                message = "-",
                cause = e
            )
        }
    }

}