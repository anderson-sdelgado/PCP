package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository

interface GetLocalList {
    suspend operator fun invoke(): Result<List<Local>>
}

class IGetLocalList(
    private val localRepository: LocalRepository
): GetLocalList {

    override suspend fun invoke(): Result<List<Local>> {
        return localRepository.list()
    }

}