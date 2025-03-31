package br.com.usinasantafe.pcp.domain.usecases.background

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.external.sharedpreferences.datasource.IConfigSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ProcessWorkManagerTest {

    private lateinit var context : Context

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `Check return retry if have mov to send`() = runTest {
//        val worker = TestListenableWorkerBuilder<ProcessWorkManager>(
//            context = context,
//        ).build()
//        val result = worker.doWork()
//        assertEquals(result, ListenableWorker.Result.retry())
    }
}