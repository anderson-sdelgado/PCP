package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SetObservResidenciaImplTest : KoinTest {

    private val usecase: SetObservResidencia by inject()
    private val movEquipResidenciaSharedPreferencesDatasource:
            MovEquipResidenciaSharedPreferencesDatasource by inject()
    private val movEquipResidenciaDao: MovEquipResidenciaDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_and_flow_add() =
        runTest {
            val result = usecase(
                observ = "Teste Observ",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaSharedPreferencesDatasourceImpl.get"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_and_flow_change() =
        runTest {
            val result = usecase(
                observ = "Teste Observ",
                flowApp = FlowApp.CHANGE,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasourceImpl.setObserv"
            )
        }

    @Test
    fun check_return_true_if_set_motorista_execute_success_and_flow_add_and_field_was_null() =
        runTest {
            movEquipResidenciaSharedPreferencesDatasource.start(
                MovEquipResidenciaSharedPreferencesModel()
            )
            val resultGetBefore = movEquipResidenciaSharedPreferencesDatasource.get()
            assertTrue(resultGetBefore.isSuccess)
            val entityBefore = resultGetBefore.getOrNull()
            assertEquals(entityBefore?.observMovEquipResidencia, null)
            val result = usecase(
                observ = "Teste Observ",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultGetAfter = movEquipResidenciaSharedPreferencesDatasource.get()
            assertTrue(resultGetAfter.isSuccess)
            val entityAfter = resultGetAfter.getOrNull()!!
            assertEquals(entityAfter.observMovEquipResidencia, "Teste Observ")
        }

    @Test
    fun check_return_true_if_set_motorista_execute_success_and_flow_add() =
        runTest {
            movEquipResidenciaSharedPreferencesDatasource.start(
                MovEquipResidenciaSharedPreferencesModel(
                    observMovEquipResidencia = "Teste"
                )
            )
            val resultGetBefore = movEquipResidenciaSharedPreferencesDatasource.get()
            assertTrue(resultGetBefore.isSuccess)
            val entityBefore = resultGetBefore.getOrNull()
            assertEquals(entityBefore?.observMovEquipResidencia, "Teste")
            val result = usecase(
                observ = "Teste Observ",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultGetAfter = movEquipResidenciaSharedPreferencesDatasource.get()
            assertTrue(resultGetAfter.isSuccess)
            val entityAfter = resultGetAfter.getOrNull()!!
            assertEquals(entityAfter.observMovEquipResidencia, "Teste Observ")
        }

    @Test
    fun check_return_true_if_set_placa_execute_success_and_flow_change() =
        runTest {
            movEquipResidenciaDao.insert(
                MovEquipResidenciaRoomModel(
                    matricVigiaMovEquipResidencia = 19759,
                    idLocalMovEquipResidencia = 1,
                    tipoMovEquipResidencia = TypeMovEquip.INPUT,
                    dthrMovEquipResidencia = 1723213270250,
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = "OBSERV TESTE",
                    statusMovEquipResidencia = StatusData.OPEN,
                    statusSendMovEquipResidencia = StatusSend.SEND,
                    statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
                )
            )
            val entityBefore = movEquipResidenciaDao.get(1)
            assertEquals(entityBefore.observMovEquipResidencia, "OBSERV TESTE")
            val result = usecase(
                observ = "Teste Observ",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val entityAfter = movEquipResidenciaDao.get(1)
            assertEquals(entityAfter.observMovEquipResidencia, "Teste Observ")
        }
}