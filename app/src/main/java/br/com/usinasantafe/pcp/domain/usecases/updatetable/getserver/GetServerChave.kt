package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Chave
import br.com.usinasantafe.pcp.domain.errors.UsecaseException
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
            if(resultToken.isFailure)
                return Result.failure(resultToken.exceptionOrNull()!!)
            val token = resultToken.getOrNull()!!
            val recoverAll = chaveRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetServerChave",
                    cause = e
                )
            )
        }
    }

}