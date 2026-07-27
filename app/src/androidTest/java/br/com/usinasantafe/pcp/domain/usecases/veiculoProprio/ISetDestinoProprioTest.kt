package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.StatusData
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

class ISetDestinoProprioTest: KoinTest {

    private val usecase: SetDestinoProprio by inject()
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()

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
    fun check_return_failure_if_not_have_data_in_mov_equip_proprio_internal_flowapp_add() = runTest {
        val exception = try {
            usecase(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(
            exception,
            null
        )
    }

    @Test
    fun check_return_failure_if_not_have_data_in_mov_equip_proprio_internal_flowapp_change() = runTest {
        val exception = try {
            usecase(
                destino = "Teste",
                flowApp = FlowApp.CHANGE,
                id = 0
            )
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(
            exception,
            null
        )
    }

    @Test
    fun check_return_success_if_have_data_in_mov_equip_proprio_internal_flowapp_add() = runTest {
        movEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
        val result = usecase(
            destino = "Teste",
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
        val resultMov = movEquipProprioSharedPreferencesDatasource.get()
        assertEquals(
            resultMov.getOrNull()!!.destinoMovEquipProprio,
            "Teste"
        )
    }

    @Test
    fun check_return_success_if_have_data_in_mov_equip_proprio_internal_flowapp_change() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 18017,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SENDING
            )
        )
        val result = usecase(
            destino = "Teste",
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
        val resultMov = movEquipProprioDao.get(1)
        assertEquals(
            resultMov.destinoMovEquipProprio,
            "Teste"
        )
        assertEquals(
            resultMov.statusSendMovEquipProprio,
            StatusSend.SEND
        )
    }
}