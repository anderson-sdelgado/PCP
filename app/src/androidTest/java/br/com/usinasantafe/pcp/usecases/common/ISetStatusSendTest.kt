package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.utils.StatusSend
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class ISetStatusSendTest: KoinTest {

    private val usecase: SetStatusSend by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(
            generateTestAppComponent(
                server.url("").toString()
            )
        )
    }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            val config = Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1,
            )
            configSharedPreferences.save(config)
            val resultGetBefore = configSharedPreferences.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val entityBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                entityBefore.statusSend,
                StatusSend.STARTED
            )
            val result = usecase(StatusSend.SENT)
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGetAfter = configSharedPreferences.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val entityAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                entityAfter.statusSend,
                StatusSend.SENT
            )
        }



}