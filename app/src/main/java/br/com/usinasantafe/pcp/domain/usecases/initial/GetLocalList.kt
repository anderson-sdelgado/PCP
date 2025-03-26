package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository

interface GetLocalList {
    suspend operator fun invoke(): Result<List<Local>>
}

class IGetLocalList(
    private val localRepository: LocalRepository
): GetLocalList {

    override suspend fun invoke(): Result<List<Local>> {
        val result = localRepository.list()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IGetLocalList",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}