package br.com.usinasantafe.pcp.domain.usecases.chaveEquip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import javax.inject.Inject

interface CloseAllMovChaveEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovChaveEquip @Inject constructor(
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
                    cause = e.cause
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
                                cause = e.cause
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