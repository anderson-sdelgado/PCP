package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import javax.inject.Inject


interface RecoverListLocal {
    suspend operator fun invoke(): List<String>
}

class RecoverListLocalImpl @Inject constructor(
    private val localRepository: LocalRepository,
): RecoverListLocal {

    override suspend fun invoke(): List<String> {
        return localRepository.listAllLocal().map { it.descrLocal }
    }

}