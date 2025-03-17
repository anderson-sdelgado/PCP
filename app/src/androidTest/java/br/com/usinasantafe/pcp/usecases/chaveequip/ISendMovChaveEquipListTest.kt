package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveEquipDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Date
import kotlin.test.assertEquals

class ISendMovChaveEquipListTest : KoinTest {

    private val usecase: SendMovChaveEquipList by inject()
    private val movChaveEquipDao: MovChaveEquipDao by inject()
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource by inject()


    @Test
    fun check_return_failure_if_not_have_data_in_mov_chave_equip_table() =
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
                "Failure Usecase -> ISendMovChaveEquipList"
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
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    matricVigiaMovChaveEquip = 19035,
                    idLocalMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> ISendMovChaveEquipList"
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
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    matricVigiaMovChaveEquip = 19035,
                    idLocalMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
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
                "Failure Datasource -> IMovChaveEquipRetrofitDatasource.send"
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
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    matricVigiaMovChaveEquip = 19035,
                    idLocalMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
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
                "Failure Datasource -> IMovChaveEquipRetrofitDatasource.send"
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
                    """[{"idMovChaveEquip":1}]"""
                )
            )
            loadKoinModules(
                generateTestAppComponent(
                    server.url("").toString()
                )
            )
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    matricVigiaMovChaveEquip = 19035,
                    idLocalMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
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
                input.idMovChaveEquip,
                1
            )
        }
}