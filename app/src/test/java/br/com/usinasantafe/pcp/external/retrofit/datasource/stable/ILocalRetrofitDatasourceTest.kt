package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.external.retrofit.api.stable.LocalApi
import br.com.usinasantafe.pcp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.LocalRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.Test

class ILocalRetrofitDatasourceTest {

    @Test
    fun `Check return failure if token is invalid`() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("{ error : Authorization header is missing }"))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: LocalApi = retrofit.create(LocalApi::class.java)
        val datasource = ILocalRetrofitDatasource(service)
        val result = datasource.recoverAll("12345")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ILocalRetrofitDatasource.recoverAll"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
        )
    }

    @Test
    fun `Check return failure if have Error 404`() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: LocalApi = retrofit.create(LocalApi::class.java)
        val datasource = ILocalRetrofitDatasource(service)
        val result = datasource.recoverAll("12345")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ILocalRetrofitDatasource.recoverAll"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            NullPointerException().toString()
        )
    }

    @Test
    fun `Check return correct`() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultLocalRetrofit))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: LocalApi = retrofit.create(LocalApi::class.java)
        val datasource = ILocalRetrofitDatasource(service)
        val result = datasource.recoverAll("12345")
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(result,
            Result.success(
                listOf(
                    LocalRetrofitModel(
                        idLocal = 1,
                        descrLocal = "Usina"
                    )
                )
            )
        )
    }

}

val resultLocalRetrofit = """
    [{"idLocal":1,"descrLocal":"Usina"}]
""".trimIndent()