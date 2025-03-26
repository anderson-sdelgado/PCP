package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken

interface GetServerTerceiro {
    suspend operator fun invoke(): Result<List<Terceiro>>
}

class IGetServerTerceiro(
    private val getToken: GetToken,
    private val terceiroRepository: TerceiroRepository
): GetServerTerceiro {

    override suspend fun invoke(): Result<List<Terceiro>> {
        try {
            val resultToken = getToken()
            if (resultToken.isFailure) {
                val e = resultToken.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerTerceiro",
                    message = e.message,
                    cause = e
                )
            }
            val token = resultToken.getOrNull()!!
            val resultRecoverAll = terceiroRepository.recoverAll(token)
            if (resultRecoverAll.isFailure) {
                val e = resultRecoverAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerTerceiro",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultRecoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetServerTerceiro",
                message = "-",
                cause = e
            )
        }
    }

}
