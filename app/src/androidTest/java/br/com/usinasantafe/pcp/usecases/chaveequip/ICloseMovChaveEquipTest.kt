package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveEquipDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
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

class ICloseMovChaveEquipTest : KoinTest {

    private val usecase: CloseMovChaveEquip by inject()
    private val movChaveEquipDao: MovChaveEquipDao by inject()

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
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipRoomDatasource.setClose"
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            val roomModel1 = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19759,
                dthrMovChaveEquip =
                1723213270250,
                tipoMovChaveEquip = TypeMovKey.REMOVE,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                observMovChaveEquip = "Teste",
                uuidMainMovChaveEquip = "UUID"
            )
            movChaveEquipDao.insert(roomModel1)
            val roomModelBefore1 =
                movChaveEquipDao.get(1)
            assertEquals(
                roomModelBefore1.statusMovChaveEquip,
                StatusData.OPEN
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val roomModelAfter1 =
                movChaveEquipDao.get(1)
            assertEquals(
                roomModelAfter1.statusMovChaveEquip,
                StatusData.CLOSE
            )
        }

}