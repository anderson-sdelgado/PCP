package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Date

class ISaveMovEquipVisitTercTest: KoinTest {

    private val usecase: SaveMovEquipVisitTerc by inject()
    private val movEquipVisitTercDao: MovEquipVisitTercDao by inject()
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource by inject()
    private val movEquipVisitTercSharedPreferencesDatasource:
            MovEquipVisitTercSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_output() =
        runTest {
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepositoryImpl.get"
            )
        }

    @Test
    fun check_return_failure_if_have_data_mov_and_not_have_config_and_output() =
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
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> SaveMovEquipVisitTercImpl"
            )
        }

    @Test
    fun check_return_failure_if_have_data_mov_config_and_without_mov_shared_preferences_and_output() =
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
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
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
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasourceImpl.get"
            )
        }

    @Test
    fun check_return_true_if_save_execute_successfully_and_output() =
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
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1,
                )
            )
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT,
                    idVisitTercMovEquipVisitTerc = 1000,
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    dthrMovEquipVisitTerc = Date(1723213270250),
                    veiculoMovEquipVisitTerc = "VEICULO TESTE",
                    placaMovEquipVisitTerc = "PLACA TESTE",
                    destinoMovEquipVisitTerc = "DESTINO TESTE",
                    observMovEquipVisitTerc = null,
                )
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val roomModel1 = movEquipVisitTercDao.get(1)
            assertEquals(roomModel1.idMovEquipVisitTerc, 1)
            assertEquals(roomModel1.tipoMovEquipVisitTerc, TypeMovEquip.INPUT)
            assertEquals(roomModel1.observMovEquipVisitTerc, "OBSERV TESTE")
            assertEquals(roomModel1.statusMovEquipForeigVisitTerc, StatusForeigner.OUTSIDE)
            val roomModel2 = movEquipVisitTercDao.get(2)
            assertEquals(roomModel2.idMovEquipVisitTerc, 2)
            assertEquals(roomModel2.tipoMovEquipVisitTerc, TypeMovEquip.OUTPUT)
            assertEquals(roomModel2.observMovEquipVisitTerc, null)
            assertEquals(roomModel2.statusMovEquipForeigVisitTerc, StatusForeigner.OUTSIDE)
        }

    @Test
    fun check_return_failure_if_not_have_config_and_input() =
        runTest {
            val result = usecase(
                typeMov = TypeMovEquip.INPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> SaveMovEquipVisitTercImpl"
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
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasourceImpl.get"
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
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                    idVisitTercMovEquipVisitTerc = 1000,
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    dthrMovEquipVisitTerc = Date(1723213270250),
                    veiculoMovEquipVisitTerc = "VEICULO TESTE",
                    placaMovEquipVisitTerc = "PLACA TESTE",
                    destinoMovEquipVisitTerc = "DESTINO TESTE",
                    observMovEquipVisitTerc = "OBSERV TESTE",
                )
            )
            val result = usecase(
                typeMov = TypeMovEquip.INPUT,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val roomModel1 = movEquipVisitTercDao.get(1)
            assertEquals(roomModel1.idMovEquipVisitTerc, 1)
            assertEquals(roomModel1.tipoMovEquipVisitTerc, TypeMovEquip.INPUT)
            assertEquals(roomModel1.observMovEquipVisitTerc, "OBSERV TESTE")
            assertEquals(roomModel1.statusMovEquipForeigVisitTerc, StatusForeigner.INSIDE)
        }

}