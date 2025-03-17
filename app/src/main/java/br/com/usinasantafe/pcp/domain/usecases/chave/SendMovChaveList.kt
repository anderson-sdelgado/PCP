package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.utils.token

interface SendMovChaveList {
    suspend operator fun invoke(): Result<List<MovChave>>
}

class ISendMovChaveList(
    private val movChaveRepository: MovChaveRepository,
    private val configRepository: ConfigRepository
): SendMovChaveList {

    override suspend fun invoke(): Result<List<MovChave>> {
        try {
            val resultListSend = movChaveRepository.listSend()
            if (resultListSend.isFailure)
                return Result.failure(resultListSend.exceptionOrNull()!!)
            val listSend = resultListSend.getOrNull()!!
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            val token = token(
                number = config.number!!,
                version = config.version!!,
                idBD = config.idBD!!
            )
            val resultSend = movChaveRepository.send(
                list = listSend,
                number = config.number!!,
                token = token
            )
            if (resultSend.isFailure)
                return Result.failure(resultSend.exceptionOrNull()!!)
            return Result.success(resultSend.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "ISendMovChaveList",
                    cause = e
                )
            )
        }
    }

}