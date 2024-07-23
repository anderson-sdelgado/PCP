package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.StatusData
import javax.inject.Inject

interface SetLocal {
    suspend operator fun invoke(pos: Int): Boolean
}

class SetLocalImpl @Inject constructor(
    private val configRepository: ConfigRepository,
    private val localRepository: LocalRepository,
): SetLocal {

    override suspend fun invoke(pos: Int): Boolean {
        try {
            val config = configRepository.getConfig()
            val local = localRepository.listAllLocal()[pos]
            config.idLocal = local.idLocal
            config.statusApont = StatusData.OPEN
            configRepository.saveConfig(config)
        } catch (exception: Exception) {
            return false
        }
        return true
    }

}