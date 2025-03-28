package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.external.room.dao.stable.ChaveDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.stable.ChaveRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ICleanChaveTest : KoinTest {

    private val usecase: br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanChave by inject()
    private val chaveDao: ChaveDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_clean_execute_correct_and_check_data() =
        runTest {
            chaveDao.insertAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - TI",
                        idLocalTrab = 1
                    )
                )
            )
            val listBefore = chaveDao.listAll()
            assertEquals(
                listBefore.size,
                1
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(true)
            )
            val listAfter = chaveDao.listAll()
            assertEquals(
                listAfter.size,
                0
            )
        }
}