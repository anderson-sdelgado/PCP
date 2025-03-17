package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.domain.errors.UsecaseException
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
            if(resultToken.isFailure)
                return Result.failure(resultToken.exceptionOrNull()!!)
            val token = resultToken.getOrNull()!!
            val recoverAll = localRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetServerLocal",
                    cause = e
                )
            )
        }
    }

}
