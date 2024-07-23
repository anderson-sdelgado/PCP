package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.utils.FlagUpdate
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface SetCheckUpdateBDConfig {
    suspend operator fun invoke(flagUpdate: FlagUpdate)
}

class SetCheckUpdateBDConfigImpl @Inject constructor(
    private val configRepository: ConfigRepository,
): SetCheckUpdateBDConfig {

    override suspend fun invoke(flagUpdate: FlagUpdate) {
        val config = configRepository.getConfig()
        config.flagUpdate = flagUpdate
        configRepository.saveConfig(config)
    }

}