package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Chave
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken

interface GetServerChave {
    suspend operator fun invoke(): Result<List<Chave>>
}

class IGetServerChave(
    private val getToken: GetToken,
    private val chaveRepository: ChaveRepository
): GetServerChave {

    override suspend fun invoke(): Result<List<Chave>> {
        try {
            val resultToken = getToken()
            if (resultToken.isFailure) {
                val e = resultToken.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerChave",
                    message = e.message,
                    cause = e
                )
            }
            val token = resultToken.getOrNull()!!
            val resultRecoverAll = chaveRepository.recoverAll(token)
            if (resultRecoverAll.isFailure) {
                val e = resultRecoverAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerChave",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultRecoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetServerChave",
                message = "-",
                cause = e
            )
        }
    }

}