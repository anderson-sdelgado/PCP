package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
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

class IGetObservProprioTest: KoinTest {

    val getObservProprio: GetObservProprio by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_success_if_get_observ_execute_successfully() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        val result = getObservProprio(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(), "TESTE OBSERV")
    }

    @Test
    fun check_return_success_if_get_observ_execute_successfully_and_field_is_null() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = null,
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        val result = getObservProprio(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(), null)
    }
}