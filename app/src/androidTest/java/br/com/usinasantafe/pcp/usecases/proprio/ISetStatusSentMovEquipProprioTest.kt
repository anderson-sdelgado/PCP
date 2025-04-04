package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
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

class ISetStatusSentMovEquipProprioTest: KoinTest {

    private val usecase: SetStatusSentMovProprio by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun verify_return_true() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        val result = usecase(
            list = listOf(
                MovEquipProprio(
                    idMovEquipProprio = 1,
                )
            )
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun verify_return_failure_if_not_have_data() = runTest {
        val result = usecase(
            list = listOf(
                MovEquipProprio(
                    idMovEquipProprio = 1,
                )
            )
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioRoomDatasourceImpl.setSent"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel.setStatusSendMovEquipProprio(br.com.usinasantafe.pcp.utils.StatusSend)' on a null object reference"
        )
    }
}