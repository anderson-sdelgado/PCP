package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
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

class SetStatusSentMovResidenciaImplTest : KoinTest {

    private val usecase: SetStatusSentMovResidencia by inject()
    private val movEquipResidenciaDao: MovEquipResidenciaDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            val list = listOf(
                MovEquipResidencia(
                    idMovEquipResidencia = 1
                ),
                MovEquipResidencia(
                    idMovEquipResidencia = 2
                )
            )
            val result = usecase(list)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasourceImpl.setSent"
            )
        }

    @Test
    fun check_return_true_if_set_status_sent_execute_success_and_check_data() =
        runTest {
            movEquipResidenciaDao.insert(
                MovEquipResidenciaRoomModel(
                    idMovEquipResidencia = 1,
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

            movEquipResidenciaDao.insert(
                MovEquipResidenciaRoomModel(
                    idMovEquipResidencia = 2,
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
            val list = listOf(
                MovEquipResidencia(
                    idMovEquipResidencia = 1
                ),
                MovEquipResidencia(
                    idMovEquipResidencia = 2
                )
            )
            val result = usecase(list)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val entity1 = movEquipResidenciaDao.get(1)
            assertEquals(entity1.statusSendMovEquipResidencia, StatusSend.SENT)
            val entity2 = movEquipResidenciaDao.get(2)
            assertEquals(entity2.statusSendMovEquipResidencia, StatusSend.SENT)
        }
}