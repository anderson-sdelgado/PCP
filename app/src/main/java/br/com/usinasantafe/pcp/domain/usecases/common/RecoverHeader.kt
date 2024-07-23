package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import javax.inject.Inject

interface RecoverHeader {
    suspend operator fun invoke(): HeaderModel
}

class RecoverHeaderImpl @Inject constructor (
    private val configRepository: ConfigRepository,
    private val colabRepository: ColabRepository,
    private val localRepository: LocalRepository,
): RecoverHeader {

    override suspend fun invoke(): HeaderModel {
        val config = configRepository.getConfig()
        return HeaderModel(
            nomeVigia = colabRepository.getColabMatric(config.matricVigia!!).nomeColab,
            local = localRepository.getLocalId(config.idLocal!!).descrLocal,
        )
    }

}