package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface RecoverNomeVigia {
    suspend operator fun invoke(): String
}

class RecoverNomeVigiaImpl @Inject constructor (
    private val configRepository: ConfigRepository,
    private val colabRepository: ColabRepository,
): RecoverNomeVigia {

    override suspend fun invoke(): String {
        val config = configRepository.getConfig()
        return colabRepository.getColabMatric(config.matricVigia!!).nomeColab
    }

}