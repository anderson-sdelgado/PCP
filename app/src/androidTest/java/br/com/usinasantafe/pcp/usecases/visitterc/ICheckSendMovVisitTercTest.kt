package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel
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

class ICheckSendMovVisitTercTest : KoinTest {

    private val usecase: CheckSendMovVisitTerc by inject()
    private val movEquipVisitTercDao: MovEquipVisitTercDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_false_if_not_have_mov_to_send() = runTest {
        val result = usecase()
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun check_return_true_if_have_mov_to_send() = runTest {
        movEquipVisitTercDao.insert(
            MovEquipVisitTercRoomModel(
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
        val result = usecase()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}