package br.com.usinasantafe.pcp.domain.usecases.common

import android.util.Log
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface CheckStatusSend {
    suspend operator fun invoke(): Boolean
}

class CheckStatusSendImpl @Inject constructor (
    private val configRepository: ConfigRepository,
): CheckStatusSend {

    override suspend fun invoke(): Boolean {
        Log.i("PCP", "Chegou aki 1 checksend")
        return when(configRepository.getConfig().statusEnvio){
            StatusSend.SENT,
            StatusSend.SENDING -> false
            StatusSend.SEND -> true
        }

    }

}