package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class ICloseAllMovChaveTest : KoinTest {

    private val usecase: CloseAllMovChave by inject()
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
    fun check_return_true_if_not_have_mov_open() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            val roomModel1 = MovChaveRoomModel(
                idMovChave = 1,
                matricVigiaMovChave = 19759,
                dthrMovChave =
                1723213270250,
                tipoMovChave = TypeMovKey.REMOVE,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE,
                observMovChave = "Teste",
                uuidMainMovChave = "UUID"
            )
            val roomModel2 = MovChaveRoomModel(
                idMovChave = 2,
                matricVigiaMovChave = 19759,
                dthrMovChave =
                1723213270250,
                tipoMovChave = TypeMovKey.REMOVE,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE,
                observMovChave = "Teste",
                uuidMainMovChave = "UUID"
            )
            movChaveDao.insert(roomModel1)
            movChaveDao.insert(roomModel2)
            val roomModelBefore1 =
                movChaveDao.get(1)
            assertEquals(
                roomModelBefore1.statusMovChave,
                StatusData.OPEN
            )
            val roomModelBefore2 =
                movChaveDao.get(2)
            assertEquals(
                roomModelBefore2.statusMovChave,
                StatusData.OPEN
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val roomModelAfter1 =
                movChaveDao.get(1)
            assertEquals(
                roomModelAfter1.statusMovChave,
                StatusData.CLOSE
            )
            val roomModelAfter2 =
                movChaveDao.get(2)
            assertEquals(
                roomModelAfter2.statusMovChave,
                StatusData.CLOSE
            )
        }

}