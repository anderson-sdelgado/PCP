package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.RLocalFluxoRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken

interface GetServerRLocalFluxo {
    suspend operator fun invoke(): Result<List<RLocalFluxo>>
}

class IGetServerRLocalFluxo(
    private val getToken: GetToken,
    private val rLocalFluxoRepository: RLocalFluxoRepository
): GetServerRLocalFluxo {

    override suspend fun invoke(): Result<List<RLocalFluxo>> {
        try {
            val resultToken = getToken()
            if (resultToken.isFailure) {
                val e = resultToken.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerRLocalFluxo",
                    message = e.message,
                    cause = e
                )
            }
            val token = resultToken.getOrNull()!!
            val resultRecoverAll = rLocalFluxoRepository.recoverAll(token)
            if (resultRecoverAll.isFailure) {
                val e = resultRecoverAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerRLocalFluxo",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultRecoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetServerRLocalFluxo",
                message = "-",
                cause = e
            )
        }
    }

}