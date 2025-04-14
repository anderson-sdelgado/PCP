package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

class ISendDataConfigTest: KoinTest {

    private val usecase: SendDataConfig by inject()

    @Test
    fun verify_return_true() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(
            MockResponse().setBody("""{"idBD":1}""")
        )
        loadKoinModules(
            generateTestAppComponent(
                server.url("/").toString()
            )
        )
        val result = usecase(
            number = "16997417840",
            version = "6.00",
            password = "12345",
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            1
        )
    }

    @Test
    fun verify_return_failure_datasource() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(
            MockResponse().setResponseCode(404)
        )
        loadKoinModules(
            generateTestAppComponent(
                server.url("/").toString()
            )
        )
        val result = usecase(
                number = "16997417840",
                version = "6.00",
                password = "12345",
            )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISendDataConfig -> IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            NullPointerException().toString()
        )
    }

    @Test
    fun verify_return_failure_usecase() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(
            MockResponse().setBody("""{"idBD":1}""")
        )
        loadKoinModules(
            generateTestAppComponent(
                server.url("/").toString()
            )
        )
        val result = usecase(
            number = "16997417840A",
            version = "6.00",
            password = "12345",
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISendDataConfig"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NumberFormatException: For input string: \"16997417840A\""
        )
    }

    @Test
    fun verify_return_failure_datasource_return_empty() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(
            MockResponse().setBody("")
        )
        loadKoinModules(
            generateTestAppComponent(
                server.url("/").toString()
            )
        )
        val result = usecase(
            number = "16997417840",
            version = "6.00",
            password = "12345",
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISendDataConfig -> IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.io.EOFException: End of input at line 1 column 1 path \$"
        )
    }
}