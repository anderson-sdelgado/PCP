package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.external.retrofit.api.stable.TerceiroApi
import br.com.usinasantafe.pcp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.TerceiroRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.Test

class ITerceiroRetrofitDatasourceTest {

    @Test
    fun `Check return failure if token is invalid`() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("{ error : Authorization header is missing }"))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: TerceiroApi = retrofit.create(TerceiroApi::class.java)
        val datasource = ITerceiroRetrofitDatasource(service)
        val result = datasource.recoverAll("12345")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ITerceiroRetrofitDatasource.recoverAll"
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
        val service: TerceiroApi = retrofit.create(TerceiroApi::class.java)
        val datasource = ITerceiroRetrofitDatasource(service)
        val result = datasource.recoverAll("12345")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ITerceiroRetrofitDatasource.recoverAll"
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
        server.enqueue(MockResponse().setBody(resultTerceiroRetrofit))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: TerceiroApi = retrofit.create(TerceiroApi::class.java)
        val datasource = ITerceiroRetrofitDatasource(service)
        val result = datasource.recoverAll("12345")
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result,
            Result.success(
                listOf(
                    TerceiroRetrofitModel(
                        idTerceiro = 1,
                        idBDTerceiro = 1,
                        cpfTerceiro = "123.456.789-00",
                        nomeTerceiro = "Terceiro",
                        empresaTerceiro = "Empresa Terceiro"
                    )
                )
            )
        )
    }

}

val resultTerceiroRetrofit = """
    [{"idTerceiro":1,"idBDTerceiro":1,"cpfTerceiro":"123.456.789-00","nomeTerceiro":"Terceiro","empresaTerceiro":"Empresa Terceiro"}]
""".trimIndent()