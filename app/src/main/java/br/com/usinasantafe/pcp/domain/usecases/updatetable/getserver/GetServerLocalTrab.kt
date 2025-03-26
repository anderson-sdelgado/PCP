package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalTrabRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken

interface GetServerLocalTrab {
    suspend operator fun invoke(): Result<List<LocalTrab>>
}

class IGetServerLocalTrab(
    private val getToken: GetToken,
    private val localTrabRepository: LocalTrabRepository
): GetServerLocalTrab {

    override suspend fun invoke(): Result<List<LocalTrab>> {
        try {
            val resultToken = getToken()
            if (resultToken.isFailure) {
                val e = resultToken.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerLocalTrab",
                    message = e.message,
                    cause = e
                )
            }
            val token = resultToken.getOrNull()!!
            val resultRecoverAll = localTrabRepository.recoverAll(token)
            if (resultRecoverAll.isFailure) {
                val e = resultRecoverAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerLocalTrab",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultRecoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetServerLocalTrab",
                message = "-",
                cause = e
            )
        }
    }

}