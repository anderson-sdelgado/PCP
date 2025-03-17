package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class ISendMovChaveListTest : KoinTest {

    private val usecase: SendMovChaveList by inject()
    private val movChaveDao: MovChaveDao by inject()
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource by inject()

    @Test
    fun check_return_failure_if_not_have_data_in_mov_chave_table() =
        runTest {
            val server = MockWebServer()
            server.start()
            loadKoinModules(
                generateTestAppComponent(
                    server.url("").toString()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> ISendMovChaveList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_config() =
        runTest {
            val server = MockWebServer()
            server.start()
            loadKoinModules(
                generateTestAppComponent(
                    server.url("").toString()
                )
            )
            movChaveDao.insert(
                MovChaveRoomModel(
                    idMovChave = 1,
                    uuidMainMovChave = "UUID",
                    dthrMovChave = 123456789,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> ISendMovChaveList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_return_data_from_api() =
        runTest {
            val server = MockWebServer()
            server.start()
            loadKoinModules(
                generateTestAppComponent(
                    server.url("").toString()
                )
            )
            movChaveDao.insert(
                MovChaveRoomModel(
                    idMovChave = 1,
                    uuidMainMovChave = "UUID",
                    dthrMovChave = 123456789,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE
                )
            )
            configSharedPreferencesDatasource.save(
                Config(
                    number = 16997417840,
                    version = "1.00",
                    idBD = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.util.concurrent.CancellationException: The test timed out"
            )
        }

    @Test
    fun verify_return_failure_datasource_return_empty() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("")
            )
            loadKoinModules(
                generateTestAppComponent(
                    server.url("").toString()
                )
            )
            movChaveDao.insert(
                MovChaveRoomModel(
                    idMovChave = 1,
                    uuidMainMovChave = "UUID",
                    dthrMovChave = 123456789,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE
                )
            )
            configSharedPreferencesDatasource.save(
                Config(
                    number = 16997417840,
                    version = "1.00",
                    idBD = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.io.EOFException: End of input at line 1 column 1 path \$"
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(
                    """[{"idMovChave":1}]"""
                )
            )
            loadKoinModules(
                generateTestAppComponent(
                    server.url("").toString()
                )
            )
            movChaveDao.insert(
                MovChaveRoomModel(
                    idMovChave = 1,
                    uuidMainMovChave = "UUID",
                    dthrMovChave = 123456789,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE
                )
            )
            configSharedPreferencesDatasource.save(
                Config(
                    number = 16997417840,
                    version = "1.00",
                    idBD = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val listInput = result.getOrNull()!!
            assertEquals(
                listInput.size,
                1
            )
            val input = listInput[0]
            assertEquals(
                input.idMovChave,
                1
            )
        }

}