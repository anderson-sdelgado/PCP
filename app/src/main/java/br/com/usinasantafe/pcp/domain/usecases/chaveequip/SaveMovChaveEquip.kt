package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.util.UUID

interface SaveMovChaveEquip {
    suspend operator fun invoke(
        typeMov: TypeMovKey,
        id: Int
    ): Result<Boolean>
}

class ISaveMovChaveEquip(
    private val configRepository: ConfigRepository,
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val startProcessSendData: StartProcessSendData
): SaveMovChaveEquip {

    override suspend fun invoke(
        typeMov: TypeMovKey,
        id: Int
    ): Result<Boolean> {
        try {
            val uuid = UUID.randomUUID()
            var uuidString = uuid.toString()
            if (typeMov == TypeMovKey.REMOVE) {
                val resultClose = movChaveEquipRepository.setOutside(id)
                if (resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
                val resultGet = movChaveEquipRepository.get(id)
                if (resultGet.isFailure)
                    return Result.failure(resultGet.exceptionOrNull()!!)
                uuidString = resultGet.getOrNull()!!.uuidMainMovChaveEquip!!
            }
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            val resultSave = movChaveEquipRepository.save(
                config.matricVigia!!,
                config.idLocal!!,
                uuidString
            )
            if(resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val idSave = resultSave.getOrNull()!!
            if (typeMov == TypeMovKey.REMOVE) {
                val resultClose = movChaveEquipRepository.setOutside(idSave)
                if (resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "ISaveMovChaveEquip",
                    cause = e
                )
            )
        }
    }

}