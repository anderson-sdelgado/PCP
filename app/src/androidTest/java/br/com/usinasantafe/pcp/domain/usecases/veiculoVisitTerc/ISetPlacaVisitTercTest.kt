package br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ISetPlacaVisitTercTest : KoinTest {

    private val usecase: SetPlacaVisitTerc by inject()
    private val movEquipVisitTercSharedPreferencesDatasource:
            MovEquipVisitTercSharedPreferencesDatasource by inject()
    private val movEquipVisitTercDao: MovEquipVisitTercDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(
            generateTestAppComponent(
                server.url("/").toString()
            )
        )
    }

    @Test
    fun check_return_failure_if_not_have_data_and_flow_add() =
        runTest {
            val result = usecase(
                placa = "Teste Placa",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetPlacaVisitTerc -> IMovEquipVisitTercRepository.setPlaca -> IMovEquipVisitTercSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_and_flow_change() =
        runTest {
            val result = usecase(
                placa = "Teste Placa",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetPlacaVisitTerc -> IMovEquipVisitTercRepository.setPlaca -> IMovEquipVisitTercRoomDatasource.setPlaca"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel.setPlacaMovEquipVisitTerc(java.lang.String)' on a null object reference"
            )
        }

    @Test
    fun check_return_true_if_set_placa_execute_success_and_flow_add() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    placaMovEquipVisitTerc = "Teste",
                )
            )
            val resultGetBefore = movEquipVisitTercSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val entityBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                entityBefore.placaMovEquipVisitTerc,
                "Teste"
            )
            val result = usecase(
                placa = "Teste Placa",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = movEquipVisitTercSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val entityAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                entityAfter.placaMovEquipVisitTerc,
                "Teste Placa"
            )
        }

    @Test
    fun check_return_true_if_set_placa_execute_success_and_flow_change() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                    idVisitTercMovEquipVisitTerc = 1000,
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    dthrMovEquipVisitTerc = 1723213270250,
                    veiculoMovEquipVisitTerc = "VEICULO TESTE",
                    placaMovEquipVisitTerc = "PLACA TESTE",
                    destinoMovEquipVisitTerc = "DESTINO TESTE",
                    observMovEquipVisitTerc = "OBSERV TESTE",
                    statusMovEquipVisitTerc = StatusData.OPEN,
                    statusSendMovEquipVisitTerc = StatusSend.SENT,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            val entityBefore = movEquipVisitTercDao.get(1)
            assertEquals(
                entityBefore.placaMovEquipVisitTerc,
                "PLACA TESTE"
            )
            val result = usecase(
                placa = "Teste Placa",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val entityAfter = movEquipVisitTercDao.get(1)
            assertEquals(
                entityAfter.placaMovEquipVisitTerc,
                "Teste Placa"
            )
        }
}