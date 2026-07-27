package br.com.usinasantafe.pcp.domain.usecases.updateTable.cleantable

import br.com.usinasantafe.pcp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ICleanColabTest : KoinTest {

    private val usecase: CleanColab by inject()
    private val colabDao: ColabDao by inject()

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
    fun check_clean_execute_correct_and_check_data() =
        runTest {
            colabDao.insertAll(
                listOf(
                    ColabRoomModel(
                        matricColab = 1,
                        nomeColab = "Anderson"
                    )
                )
            )
            val listBefore = colabDao.listAll()
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
                result.getOrNull()!!,
                true
            )
            val listAfter = colabDao.listAll()
            assertEquals(
                listAfter.size,
                0
            )
        }
}