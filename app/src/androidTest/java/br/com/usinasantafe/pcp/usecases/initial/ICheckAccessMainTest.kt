package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.usecases.config.CheckAccessMain
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

class ICheckAccessMainTest: KoinTest {

    private val usecase: CheckAccessMain by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun verify_return_false_if_not_data_config() = runTest {
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun verify_return_false_if_data_config_field_flag_update_is_outdated() = runTest {
        configSharedPreferences.save(Config())
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun verify_return_true_if_data_config_field_flag_update_is_updated() = runTest {
        configSharedPreferences.save(
            Config(flagUpdate = FlagUpdate.UPDATED)
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, true)
    }

}