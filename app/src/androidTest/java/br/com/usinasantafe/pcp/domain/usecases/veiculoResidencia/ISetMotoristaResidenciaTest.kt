package br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ISetMotoristaResidenciaTest : KoinTest {

    private val usecase: SetMotoristaResidencia by inject()
    private val movEquipResidenciaSharedPreferencesDatasource:
            MovEquipResidenciaSharedPreferencesDatasource by inject()
    private val movEquipResidenciaDao: MovEquipResidenciaDao by inject()

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
                motorista = "Teste Motorista",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetMotoristaResidencia -> IMovEquipResidenciaRepository.setMotorista -> IMovEquipResidenciaSharedPreferencesDatasource.get"
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
                motorista = "Teste Motorista",
                flowApp = FlowApp.CHANGE,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetMotoristaResidencia -> IMovEquipResidenciaRepository.setMotorista -> IMovEquipResidenciaRoomDatasource.setMotorista"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel.setMotoristaMovEquipResidencia(java.lang.String)' on a null object reference"
            )
        }

    @Test
    fun check_return_true_if_set_motorista_execute_success_and_flow_add_and_field_was_null() =
        runTest {
            movEquipResidenciaSharedPreferencesDatasource.save(
                MovEquipResidenciaSharedPreferencesModel()
            )
            val resultGetBefore = movEquipResidenciaSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val entityBefore = resultGetBefore.getOrNull()
            assertEquals(
                entityBefore?.motoristaMovEquipResidencia,
                null
            )
            val result = usecase(
                motorista = "Teste Motorista",
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
            val resultGetAfter = movEquipResidenciaSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val entityAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                entityAfter.motoristaMovEquipResidencia,
                "Teste Motorista"
            )
        }

    @Test
    fun check_return_true_if_set_motorista_execute_success_and_flow_add() =
        runTest {
            movEquipResidenciaSharedPreferencesDatasource.save(
                MovEquipResidenciaSharedPreferencesModel(
                    motoristaMovEquipResidencia = "Teste"
                )
            )
            val resultGetBefore = movEquipResidenciaSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val entityBefore = resultGetBefore.getOrNull()
            assertEquals(
                entityBefore?.motoristaMovEquipResidencia,
                "Teste"
            )
            val result = usecase(
                motorista = "Teste Motorista",
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
            val resultGetAfter = movEquipResidenciaSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val entityAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                entityAfter.motoristaMovEquipResidencia,
                "Teste Motorista"
            )
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
            assertEquals(
                entityBefore.motoristaMovEquipResidencia,
                "MOTORISTA TESTE"
            )
            val result = usecase(
                motorista = "Teste Motorista",
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
            val entityAfter = movEquipResidenciaDao.get(1)
            assertEquals(
                entityAfter.motoristaMovEquipResidencia,
                "Teste Motorista"
            )
        }
}