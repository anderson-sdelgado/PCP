package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ISetCheckUpdateAllTableTest: KoinTest {

    private val usecase: SetCheckUpdateAllTable by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_success_if_data_is_correct() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345",
            version = "6.00",
            idBD = 1,
        )
        configSharedPreferences.save(config)
        val result = usecase(FlagUpdate.UPDATED)
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
    }

}