package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.utils.StatusSend
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class IGetStatusSendTest : KoinTest {

    private val usecase: GetStatusSend by inject()
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
            val resultFlow = usecase()
            val list = resultFlow.toList()
            assertEquals(
                list.count(),
                1
            )
            val result = list[0]
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusSend.STARTED
            )
        }

    @Test
    fun check_return_true_and_data_altered() =
        runTest {
            configSharedPreferences.save(
                Config(
                    statusSend = StatusSend.SENT
                )
            )
            val resultFlow = usecase()
            val list = resultFlow.toList()
            assertEquals(
                list.count(),
                1
            )
            val result = list[0]
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusSend.SENT
            )
        }
}