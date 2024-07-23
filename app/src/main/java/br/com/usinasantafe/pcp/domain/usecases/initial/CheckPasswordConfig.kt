package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface CheckPasswordConfig {
    suspend operator fun invoke(senha: String): Boolean
}

class CheckPasswordConfigImpl @Inject constructor(
    private val configRepository: ConfigRepository
): CheckPasswordConfig {

    override suspend fun invoke(senha: String): Boolean {
        if(!configRepository.hasConfig())
            return true
        if(configRepository.getConfig().passwordConfig.isNullOrEmpty())
            return true
        if(configRepository.getConfig().passwordConfig == senha)
            return true
        return false
    }

}