package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.external.retrofit.api.variable.ConfigApi
import br.com.usinasantafe.pcp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test


class IConfigRetrofitDatasourceTest {

    @Test
    fun `Check return correct`() = runTest {
        val configRetrofitModelOutput = ConfigRetrofitModelOutput(
            version = "6.00",
            number = 16997417840,
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""{"idBD":1}"""))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: ConfigApi = retrofit.create(ConfigApi::class.java)
        val datasource = IConfigRetrofitDatasource(service)
        val result = datasource.recoverToken(configRetrofitModelOutput)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(), ConfigRetrofitModelInput(idBD = 1))
    }

    @Test
    fun `Check return failure if have Error 404`() = runTest {
        val configRetrofitModelOutput = ConfigRetrofitModelOutput(
            version = "6.00",
            number = 16997417840,
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: ConfigApi = retrofit.create(ConfigApi::class.java)
        val datasource = IConfigRetrofitDatasource(service)
        val result = datasource.recoverToken(configRetrofitModelOutput)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRetrofitDatasourceImpl.recoverToken")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun `Check return failure if data sent incorrect`() = runTest {
        val configRetrofitModelOutput = ConfigRetrofitModelOutput(
            version = "6.00",
            number = 16997417840,
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("Failure Connection BD"))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: ConfigApi = retrofit.create(ConfigApi::class.java)
        val datasource = IConfigRetrofitDatasource(service)
        val result = datasource.recoverToken(configRetrofitModelOutput)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRetrofitDatasourceImpl.recoverToken")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$")
    }

    @Test
    fun `Check return failure if have data incorrect`() = runTest {
        val configRetrofitModelOutput = ConfigRetrofitModelOutput(
            version = "6.00",
            number = 16997417840,
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""{"idBD":1a}"""))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: ConfigApi = retrofit.create(ConfigApi::class.java)
        val datasource = IConfigRetrofitDatasource(service)
        val result = datasource.recoverToken(configRetrofitModelOutput)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRetrofitDatasourceImpl.recoverToken")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 9 path \$.idBD")
    }
}
