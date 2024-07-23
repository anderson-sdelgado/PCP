package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.presenter.initial.config.ConfigModel
import br.com.usinasantafe.pcp.presenter.initial.config.toConfigModel
import javax.inject.Inject

interface RecoverConfig {
    suspend operator fun invoke(): ConfigModel?
}

class RecoverConfigImpl @Inject constructor (
    private val configRepository: ConfigRepository
): RecoverConfig {

    override suspend fun invoke(): ConfigModel? {
        if (!configRepository.hasConfig())
            return null
        if(configRepository.getConfig().passwordConfig.isNullOrEmpty())
            return null
        return configRepository.getConfig().toConfigModel()
    }

}