package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveEquipDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveEquipSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.utils.FlowApp
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

class ISetMatricColabMovChaveEquipTest: KoinTest {

    private val usecase: SetMatricColabMovChaveEquip by inject()
    private val movChaveEquipSharedPreferencesDatasource:
            MovChaveEquipSharedPreferencesDatasource by inject()
    private val movChavEquipeDao: MovChaveEquipDao by inject()

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
    fun check_return_failure_if_not_have_started_mov_chave_equip_shared_preference_add() =
        runTest {
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_matric_incorrect() =
        runTest {
            val result = usecase(
                matricColab = "19759Asada",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> ISetMatricColabMovChaveEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"19759Asada\""
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            movChaveEquipSharedPreferencesDatasource.start()
            val resultGetBefore = movChaveEquipSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()
            assertEquals(
                modelBefore!!.matricColabMovChaveEquip,
                null
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGetAfter = movChaveEquipSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val movChave = resultGetAfter.getOrNull()!!
            assertEquals(
                movChave.matricColabMovChaveEquip,
                19759
            )
        }

    @Test
    fun check_return_failure_if_not_have_started_mov_chave_equip_room_change() =
        runTest {
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipRoomDatasource.setMatricColab"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel.setMatricColabMovChaveEquip(int)' on a null object reference"
            )
        }

    @Test
    fun check_return_true_and_data_returned_change() =
        runTest {
            movChavEquipeDao.insert(
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
            val listRoomBefore = movChavEquipeDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listRoomBefore.size,
                1
            )
            val entityRoomBefore = listRoomBefore[0]
            assertEquals(
                entityRoomBefore.matricColabMovChaveEquip,
                19035
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val listRoomAfter = movChavEquipeDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listRoomAfter.size,
                1
            )
            val entityRoomAfter = listRoomAfter[0]
            assertEquals(
                entityRoomAfter.matricColabMovChaveEquip,
                19759
            )
        }

}