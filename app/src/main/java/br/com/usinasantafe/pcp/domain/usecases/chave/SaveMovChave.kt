package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.util.UUID

interface SaveMovChave {
    suspend operator fun invoke(
        typeMov: TypeMovKey,
        id: Int
    ): Result<Boolean>
}

class ISaveMovChave(
    private val configRepository: ConfigRepository,
    private val movChaveRepository: MovChaveRepository,
    private val startProcessSendData: StartProcessSendData
): SaveMovChave {

    override suspend fun invoke(
        typeMov: TypeMovKey,
        id: Int
    ): Result<Boolean> {
        try {
            val uuid = UUID.randomUUID()
            var uuidString = uuid.toString()
            if (typeMov == TypeMovKey.RECEIPT) {
                val resultClose = movChaveRepository.setOutside(id)
                if (resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
                val resultGet = movChaveRepository.get(id)
                if (resultGet.isFailure)
                    return Result.failure(resultGet.exceptionOrNull()!!)
                uuidString = resultGet.getOrNull()!!.uuidMainMovChave!!
            }
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            val resultSave = movChaveRepository.save(
                config.matricVigia!!,
                config.idLocal!!,
                uuidString
            )
            if(resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val idSave = resultSave.getOrNull()!!
            if (typeMov == TypeMovKey.RECEIPT) {
                val resultClose = movChaveRepository.setOutside(idSave)
                if (resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SaveMovChaveImpl",
                    cause = e
                )
            )
        }
    }

}