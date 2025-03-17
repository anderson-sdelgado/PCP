package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
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
import java.util.Date
import kotlin.test.assertEquals

class ISetStatusSentMovChaveEquipTest: KoinTest {

    private val usecase: SetStatusSentMovChaveEquip by inject()
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
            val result = usecase(
                list = listOf(
                    MovChaveEquip(
                        idMovChaveEquip = 1,
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipRoomDatasource.setSent"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel.setStatusSendMovChaveEquip(br.com.usinasantafe.pcp.utils.StatusSend)' on a null object reference"
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val listBefore = movChaveEquipDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listBefore.size,
                1
            )
            val entityBefore = listBefore[0]
            assertEquals(
                entityBefore.statusSendMovChaveEquip,
                StatusSend.SEND
            )
            val result = usecase(
                list = listOf(
                    MovChaveEquip(
                        idMovChaveEquip = 1,
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
            val listAfter = movChaveEquipDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listAfter.size,
                1
            )
            val entityAfter = listAfter[0]
            assertEquals(
                entityAfter.statusSendMovChaveEquip,
                StatusSend.SENT
            )
        }
}