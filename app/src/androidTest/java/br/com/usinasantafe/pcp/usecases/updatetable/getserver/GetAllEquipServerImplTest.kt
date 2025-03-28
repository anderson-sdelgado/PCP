package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class RecoverEquipServerImplTest : KoinTest {

    private val usecase: GetServerEquip by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Test
    fun verify_return_failure_usecase_if_not_have_data_config_internal() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultEquip))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverEquipServer")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun verify_return_failure_datasource_if_access_invalid_page() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.save(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRetrofitDatasourceImpl.recoverAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun verify_return_failure_datasource_if_token_invalid() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(tokenInvalidEquip))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.save(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRetrofitDatasourceImpl.recoverAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$")
    }

    @Test
    fun verify_return_failure_datasource_if_data_incorrect() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultEquipIncorrect))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.save(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRetrofitDatasourceImpl.recoverAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 27 path \$[0].nroEquip")
    }

    @Test
    fun verify_return_data_if_success_usecase() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultEquip))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.save(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        val resultList = result.getOrNull()!!
        val resultEquip = resultList[0]
        assertEquals(resultEquip.idEquip, 10)
        assertEquals(resultEquip.nroEquip, 100)
    }

}

val resultEquip = """
    [{"idEquip":10,"nroEquip":100}]
""".trimIndent()

val resultEquipIncorrect = """
    [{"idEquip":10,"nroEquip":100a}]
""".trimIndent()

val tokenInvalidEquip = """
    {
        "error": "Invalid token"
    }
""".trimIndent()