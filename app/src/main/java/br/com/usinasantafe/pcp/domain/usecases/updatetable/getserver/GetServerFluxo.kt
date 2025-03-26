package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.FluxoRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken

interface GetServerFluxo {
    suspend operator fun invoke(): Result<List<Fluxo>>
}

class IGetServerFluxo(
    private val getToken: GetToken,
    private val fluxoRepository: FluxoRepository
): GetServerFluxo {

    override suspend fun invoke(): Result<List<Fluxo>> {
        try {
            val resultToken = getToken()
            if (resultToken.isFailure) {
                val e = resultToken.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerFluxo",
                    message = e.message,
                    cause = e
                )
            }
            val token = resultToken.getOrNull()!!
            val resultRecoverAll = fluxoRepository.recoverAll(token)
            if (resultRecoverAll.isFailure) {
                val e = resultRecoverAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerFluxo",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultRecoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetServerFluxo",
                message = "-",
                cause = e
            )
        }
    }

}