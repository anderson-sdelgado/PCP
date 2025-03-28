package br.com.usinasantafe.pcp.domain.usecases.residencia

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

class GetMovEquipResidenciaInsideListImplTest : KoinTest {

    private val usecase: GetMovEquipResidenciaInsideList by inject()
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
            val result = usecase()
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!.size, 0)
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            val roomModel1 = MovEquipResidenciaRoomModel(
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
            val roomModel2 = MovEquipResidenciaRoomModel(
                matricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE 2",
                veiculoMovEquipResidencia = "VEICULO TESTE 2",
                placaMovEquipResidencia = "PLACA TESTE 2",
                observMovEquipResidencia = "OBSERV TESTE 2",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            movEquipResidenciaDao.insert(roomModel1)
            movEquipResidenciaDao.insert(roomModel2)
            val result = usecase()
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 2)
            assertEquals(list[0].id, 1)
            assertEquals(list[1].id, 2)
            assertEquals(list[0].placa, "PLACA TESTE")
            assertEquals(list[1].placa, "PLACA TESTE 2")
            assertEquals(list[0].tipoMov, null)
            assertEquals(list[1].tipoMov, null)
        }


}