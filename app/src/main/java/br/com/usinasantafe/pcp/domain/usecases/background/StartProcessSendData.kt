package br.com.usinasantafe.pcp.domain.usecases.background

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import br.com.usinasantafe.pcp.domain.usecases.common.SetStatusSend
import br.com.usinasantafe.pcp.utils.StatusSend
import java.util.concurrent.TimeUnit

interface StartProcessSendData {
    suspend operator fun invoke()
}

class IStartProcessSendData(
    private val workManager: WorkManager,
    private val setStatusSend: SetStatusSend
) : StartProcessSendData {

    override suspend fun invoke() {
        setStatusSend(StatusSend.SEND)
        val constraints = Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequest
            .Builder(ProcessWorkManager::class.java)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                2, TimeUnit.MINUTES
            )
            .build()
        workManager.enqueueUniqueWork("WORKMANAGER-PCP", ExistingWorkPolicy.REPLACE, workRequest)
    }

}