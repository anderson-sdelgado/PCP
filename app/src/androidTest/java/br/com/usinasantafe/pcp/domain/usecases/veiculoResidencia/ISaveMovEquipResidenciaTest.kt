package br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
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
import java.util.Date

class ISaveMovEquipResidenciaTest : KoinTest {

    private val usecase: SaveMovEquipResidencia by inject()
    private val movEquipResidenciaDao: MovEquipResidenciaDao by inject()
    private val configSharedPreferencesDatasource:
            ConfigSharedPreferencesDatasource by inject()
    private val movEquipResidenciaSharedPreferencesDatasource:
            MovEquipResidenciaSharedPreferencesDatasource by inject()

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
    fun check_return_failure_if_not_have_data_output() =
        runTest {
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovEquipResidencia -> IMovEquipResidenciaRepository.setOutside -> IMovEquipResidenciaRoomDatasource.setOutside"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel.setStatusMovEquipForeignerResidencia(br.com.usinasantafe.pcp.utils.StatusForeigner)' on a null object reference"
            )
        }

    @Test
    fun check_return_failure_if_have_data_mov_and_not_have_config_and_output() =
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
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovEquipResidencia"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_have_data_mov_config_and_without_mov_shared_preferences_and_output() =
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
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1,
                )
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovEquipResidencia -> IMovEquipResidenciaRepository.save -> IMovEquipResidenciaSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_if_save_execute_successfully_and_output() =
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
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1,
                )
            )
            movEquipResidenciaSharedPreferencesDatasource.start(
                MovEquipResidenciaSharedPreferencesModel(
                    tipoMovEquipResidencia = TypeMovEquip.OUTPUT,
                    dthrMovEquipResidencia = Date(1723213270250),
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = null,
                )
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
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
            val roomModel1 = movEquipResidenciaDao.get(1)
            assertEquals(
                roomModel1.idMovEquipResidencia,
                1
            )
            assertEquals(
                roomModel1.tipoMovEquipResidencia,
                TypeMovEquip.INPUT
            )
            assertEquals(
                roomModel1.observMovEquipResidencia,
                "OBSERV TESTE"
            )
            assertEquals(
                roomModel1.statusMovEquipForeignerResidencia,
                StatusForeigner.OUTSIDE
            )
            val roomModel2 = movEquipResidenciaDao.get(2)
            assertEquals(
                roomModel2.idMovEquipResidencia,
                2
            )
            assertEquals(
                roomModel2.tipoMovEquipResidencia,
                TypeMovEquip.OUTPUT
            )
            assertEquals(
                roomModel2.observMovEquipResidencia,
                null
            )
            assertEquals(
                roomModel2.statusMovEquipForeignerResidencia,
                StatusForeigner.OUTSIDE
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_input() =
        runTest {
            val result = usecase(
                typeMov = TypeMovEquip.INPUT,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovEquipResidencia"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_have_data_config_and_without_mov_shared_preferences_and_input() =
        runTest {
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1,
                )
            )
            val result = usecase(
                typeMov = TypeMovEquip.INPUT,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovEquipResidencia -> IMovEquipResidenciaRepository.save -> IMovEquipResidenciaSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_if_save_execute_successfully_and_input() =
        runTest {
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1,
                )
            )
            movEquipResidenciaSharedPreferencesDatasource.start(
                MovEquipResidenciaSharedPreferencesModel(
                    tipoMovEquipResidencia = TypeMovEquip.INPUT,
                    dthrMovEquipResidencia = Date(1723213270250),
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = "OBSERV TESTE",
                )
            )
            val result = usecase(
                typeMov = TypeMovEquip.INPUT,
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
            val roomModel1 = movEquipResidenciaDao.get(1)
            assertEquals(
                roomModel1.idMovEquipResidencia,
                1
            )
            assertEquals(
                roomModel1.tipoMovEquipResidencia,
                TypeMovEquip.INPUT
            )
            assertEquals(
                roomModel1.observMovEquipResidencia,
                "OBSERV TESTE"
            )
            assertEquals(
                roomModel1.statusMovEquipForeignerResidencia,
                StatusForeigner.INSIDE
            )
        }
}