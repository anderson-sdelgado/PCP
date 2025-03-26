package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken

interface GetServerLocal {
    suspend operator fun invoke(): Result<List<Local>>
}

class IGetServerLocal(
    private val getToken: GetToken,
    private val localRepository: LocalRepository
): GetServerLocal {

    override suspend fun invoke(): Result<List<Local>> {
        try {
            val resultToken = getToken()
            if (resultToken.isFailure) {
                val e = resultToken.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerLocal",
                    message = e.message,
                    cause = e
                )
            }
            val token = resultToken.getOrNull()!!
            val resultRecoverAll = localRepository.recoverAll(token)
            if (resultRecoverAll.isFailure) {
                val e = resultRecoverAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerLocal",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultRecoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetServerLocal",
                message = "-",
                cause = e
            )
        }
    }

}
