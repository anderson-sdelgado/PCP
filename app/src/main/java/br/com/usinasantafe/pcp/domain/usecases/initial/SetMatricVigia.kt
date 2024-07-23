package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import javax.inject.Inject


interface SetMatricVigia {
    suspend operator fun invoke(matricVigia: String): Boolean
}

class SetMatricVigiaImpl @Inject constructor(
    private val configRepository: ConfigRepository,
): SetMatricVigia {
    override suspend fun invoke(matricVigia: String): Boolean {
        try {
            val config = configRepository.getConfig()
            config.matricVigia = matricVigia.toLong()
            configRepository.saveConfig(config)
        } catch (exception: Exception) {
            return false
        }
        return true
    }
}