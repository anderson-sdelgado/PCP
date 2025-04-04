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

class GetDetalheResidenciaImplTest : KoinTest {

    private val usecase: GetDetalheResidencia by inject()
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
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepositoryImpl.get"
            )
        }

    @Test
    fun check_return_true_and_field_return_if_get_detalhe_execute_success() =
        runTest {
            val roomModel = MovEquipResidenciaRoomModel(
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
            movEquipResidenciaDao.insert(roomModel)
            val result = usecase(1)
            assertTrue(result.isSuccess)
            val entity = result.getOrNull()!!
            assertEquals(
                entity.motorista,
                "MOTORISTA TESTE"
            )
            assertEquals(
                entity.tipoMov,
                "ENTRADA"
            )
            assertEquals(
                entity.placa,
                "PLACA TESTE"
            )
        }

}