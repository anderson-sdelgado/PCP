package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovChaveApi
import br.com.usinasantafe.pcp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelOutput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.test.assertEquals

class IMovChaveRetrofitDatasourceTest {

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val retrofitModelOutput = MovChaveRetrofitModelOutput(
                uuidMainMovChave = "UUID",
                idMovChave = 1,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                tipoMovChave = 1,
                dthrMovChave = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "TESTE OBSERV",
                nroAparelhoMovChave = 16997417840L
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(
                    """[{"idMovChave":1}]"""
                )
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service: MovChaveApi = retrofit.create(MovChaveApi::class.java)
            val datasource = IMovChaveRetrofitDatasource(service)
            val result = datasource.send(
                listOf(
                    retrofitModelOutput
                )
                , "123456"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val retrofitModelInputList = result.getOrNull()!!
            assertEquals(
                retrofitModelInputList.size,
                1
            )
            val retrofitModelInput = retrofitModelInputList[0]
            assertEquals(
                retrofitModelInput.idMovChave,
                1
            )
        }

    @Test
    fun `send - Check return failure if have Error 404`() =
        runTest {
            val retrofitModelOutput = MovChaveRetrofitModelOutput(
                uuidMainMovChave = "UUID",
                idMovChave = 1,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                tipoMovChave = 1,
                dthrMovChave = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "TESTE OBSERV",
                nroAparelhoMovChave = 16997417840L
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service: MovChaveApi = retrofit.create(MovChaveApi::class.java)
            val datasource = IMovChaveRetrofitDatasource(service)
            val result = datasource.send(
                listOf(
                    retrofitModelOutput
                )
                , "123456"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `send - Check return failure if data sent incorrect`() =
        runTest {
            val retrofitModelOutput = MovChaveRetrofitModelOutput(
                uuidMainMovChave = "UUID",
                idMovChave = 1,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                tipoMovChave = 1,
                dthrMovChave = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "TESTE OBSERV",
                nroAparelhoMovChave = 16997417840L
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("Failure Connection BD")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service: MovChaveApi = retrofit.create(MovChaveApi::class.java)
            val datasource = IMovChaveRetrofitDatasource(service)
            val result = datasource.send(
                listOf(
                    retrofitModelOutput
                )
                , "123456"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
            )
        }

    @Test
    fun `Check return failure if have data incorrect`() =
        runTest {
            val retrofitModelOutput = MovChaveRetrofitModelOutput(
                uuidMainMovChave = "UUID",
                idMovChave = 1,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                tipoMovChave = 1,
                dthrMovChave = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "TESTE OBSERV",
                nroAparelhoMovChave = 16997417840L
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(
                    """[{"idMovChave":1a}]"""
                )
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service: MovChaveApi = retrofit.create(MovChaveApi::class.java)
            val datasource = IMovChaveRetrofitDatasource(service)
            val result = datasource.send(
                listOf(
                    retrofitModelOutput
                )
                , "123456"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 16 path \$[0].idMovChave"
            )
        }
}