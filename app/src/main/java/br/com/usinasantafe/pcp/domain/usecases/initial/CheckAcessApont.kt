package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.utils.FlagUpdate
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface CheckAcessApont {
    suspend operator fun invoke(): Boolean
}

class CheckAcessApontImpl @Inject constructor(
    private val configRepository: ConfigRepository
): CheckAcessApont {

    override suspend fun invoke(): Boolean {
        if(configRepository.getConfig().flagUpdate == FlagUpdate.OUTDATED)
            return false
        return true
    }

}