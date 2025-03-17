package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class ISetStatusSentMovChaveTest: KoinTest {

    private val usecase: SetStatusSentMovChave by inject()
    private val movChaveDao: MovChaveDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(
            generateTestAppComponent(
                server.url("").toString()
            )
        )
    }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            val result = usecase(
                list = listOf(
                    MovChave(
                        idMovChave = 1,
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveRoomDatasource.setSent"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel.setStatusSendMovChave(br.com.usinasantafe.pcp.utils.StatusSend)' on a null object reference"
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
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
            val listBefore = movChaveDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listBefore.size,
                1
            )
            val entityBefore = listBefore[0]
            assertEquals(
                entityBefore.statusSendMovChave,
                StatusSend.SEND
            )
            val result = usecase(
                list = listOf(
                    MovChave(
                        idMovChave = 1,
                    )
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val listAfter = movChaveDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listAfter.size,
                1
            )
            val entityAfter = listAfter[0]
            assertEquals(
                entityAfter.statusSendMovChave,
                StatusSend.SENT
            )
        }

}