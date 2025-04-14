package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ISaveTerceiroTest: KoinTest {

    private val usecase: SaveTerceiro by inject()
    private val terceiroDao: TerceiroDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(
            generateTestAppComponent(
                server.url("").toString()
            )
        )
    }

    @Test
    fun check_return_success_if_data_is_correct() = runTest {
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "123.456.789-00",
                nomeTerceiro = "Terceiro",
                empresaTerceiro = "Empresa Terceiro",
            )
        )
        val result = usecase(terceiroList)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
        val listBefore = terceiroDao.listAll()
        assertEquals(
            listBefore.size,
            1
        )
    }

    @Test
    fun check_return_failure_if_have_row_repeated() = runTest {
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "123.456.789-00",
                nomeTerceiro = "Terceiro",
                empresaTerceiro = "Empresa Terceiro",
            ),
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "123.456.789-00",
                nomeTerceiro = "Terceiro",
                empresaTerceiro = "Empresa Terceiro",
            )
        )
        val result = usecase(terceiroList)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISaveTerceiro -> ITerceiroRepository.addAll -> ITerceiroRoomDatasource.addAll"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_terceiro.idTerceiro (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])"
        )
    }

}
