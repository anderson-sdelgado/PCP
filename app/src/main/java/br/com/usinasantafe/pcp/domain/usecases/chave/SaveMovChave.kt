package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.TypeMovKey
import br.com.usinasantafe.pcp.utils.UUIDProvider
import br.com.usinasantafe.pcp.utils.UUIDProvider.uuid

interface SaveMovChave {
    suspend operator fun invoke(
        typeMov: TypeMovKey,
        id: Int
    ): Result<Boolean>
}

class ISaveMovChave(
    private val configRepository: ConfigRepository,
    private val movChaveRepository: MovChaveRepository,
    private val startProcessSendData: StartProcessSendData,
    private val uuidProvider: UUIDProvider = UUIDProvider
): SaveMovChave {

    override suspend fun invoke(
        typeMov: TypeMovKey,
        id: Int
    ): Result<Boolean> {
        try {
            var uuidString = uuidProvider.uuid()
            if (typeMov == TypeMovKey.RECEIPT) {
                val resultClose = movChaveRepository.setOutside(id)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISaveMovChave",
                        message = e.message,
                        cause = e
                    )
                }
                val resultGet = movChaveRepository.get(id)
                if (resultGet.isFailure) {
                    val e = resultGet.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISaveMovChave",
                        message = e.message,
                        cause = e
                    )
                }
                uuidString = resultGet.getOrNull()!!.uuidMainMovChave!!
            }
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovChave",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultConfig.getOrNull()!!
            val resultSave = movChaveRepository.save(
                config.matricVigia!!,
                config.idLocal!!,
                uuidString
            )
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveMovChave",
                    message = e.message,
                    cause = e
                )
            }
            val idSave = resultSave.getOrNull()!!
            if (typeMov == TypeMovKey.RECEIPT) {
                val resultClose = movChaveRepository.setOutside(idSave)
                if (resultClose.isFailure) {
                    val e = resultClose.exceptionOrNull()!!
                    return resultFailure(
                        context = "ISaveMovChave",
                        message = e.message,
                        cause = e
                    )
                }
            }
            startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISaveMovChave",
                message = "-",
                cause = e
            )
        }
    }

}