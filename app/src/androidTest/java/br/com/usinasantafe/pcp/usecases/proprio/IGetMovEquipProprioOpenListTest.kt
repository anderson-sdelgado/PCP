package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
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

class IGetMovEquipProprioOpenListTest: KoinTest {

    private val usecase: GetMovEquipProprioOpenList by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()
    private val equipDao: EquipDao by inject()
    private val colabDao: ColabDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_list_empty_if_data_is_empty() = runTest {
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.size, 0)
    }

    @Test
    fun check_return_failure_if_not_have_equip_with_id_researched() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
                notaFiscalMovEquipProprio = null,
                observMovEquipProprio = null,
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverMovEquipProprioOpenList")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }

    @Test
    fun check_return_success_if_have_all_data_correct() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
                notaFiscalMovEquipProprio = null,
                observMovEquipProprio = null,
            )
        )
        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 1,
                    nroEquip = 100,
                    descrEquip = "teste"
                )
            )
        )
        colabDao.insertAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )
        val result = usecase()
        assertTrue(result.isSuccess)
    }
}