package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovChaveApi
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovChaveEquipApi
import br.com.usinasantafe.pcp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelOutput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.test.assertEquals

class IMovChaveEquipRetrofitDatasourceTest {

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val retrofitModelOutput = MovChaveEquipRetrofitModelOutput(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                dthrMovChaveEquip = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                tipoMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
                uuidMainMovChaveEquip = "UUID",
                nroAparelhoMovChaveEquip = 16997417840
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(
                    """[{"idMovChaveEquip":1}]"""
                )
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service: MovChaveEquipApi = retrofit.create(MovChaveEquipApi::class.java)
            val datasource = IMovChaveEquipRetrofitDatasource(service)
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
                retrofitModelInput.idMovChaveEquip,
                1
            )
        }

    @Test
    fun `send - Check return failure if have Error 404`() =
        runTest {
            val retrofitModelOutput = MovChaveEquipRetrofitModelOutput(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                dthrMovChaveEquip = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                tipoMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
                uuidMainMovChaveEquip = "UUID",
                nroAparelhoMovChaveEquip = 16997417840
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service: MovChaveEquipApi = retrofit.create(MovChaveEquipApi::class.java)
            val datasource = IMovChaveEquipRetrofitDatasource(service)
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
                "IMovChaveEquipRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `send - Check return failure if data sent incorrect`() =
        runTest {
            val retrofitModelOutput = MovChaveEquipRetrofitModelOutput(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                dthrMovChaveEquip = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                tipoMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
                uuidMainMovChaveEquip = "UUID",
                nroAparelhoMovChaveEquip = 16997417840
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("Failure Connection BD")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service: MovChaveEquipApi = retrofit.create(MovChaveEquipApi::class.java)
            val datasource = IMovChaveEquipRetrofitDatasource(service)
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
                "IMovChaveEquipRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
            )
        }

    @Test
    fun `Check return failure if have data incorrect`() =
        runTest {
            val retrofitModelOutput = MovChaveEquipRetrofitModelOutput(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                dthrMovChaveEquip = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                tipoMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
                uuidMainMovChaveEquip = "UUID",
                nroAparelhoMovChaveEquip = 16997417840
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(
                    """[{"idMovChaveEquip":1a}]"""
                )
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service: MovChaveEquipApi = retrofit.create(MovChaveEquipApi::class.java)
            val datasource = IMovChaveEquipRetrofitDatasource(service)
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
                "IMovChaveEquipRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 21 path \$[0].idMovChaveEquip"
            )
        }
}