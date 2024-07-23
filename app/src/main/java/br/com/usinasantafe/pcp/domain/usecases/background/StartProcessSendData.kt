package br.com.usinasantafe.pcp.domain.usecases.background

import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import br.com.usinasantafe.pcp.domain.usecases.common.CheckStatusSend
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface StartProcessSendData {
    suspend operator fun invoke()
}

class StartProcessSendDataImpl @Inject constructor(
    private val workManager: WorkManager,
) : StartProcessSendData {

    override suspend fun invoke() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
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