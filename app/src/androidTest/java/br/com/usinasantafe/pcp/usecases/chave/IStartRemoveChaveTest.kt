package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class IStartRemoveChaveTest : KoinTest {

    private val usecase: StartRemoveMovChave by inject()
    private val movChaveSharedPreferencesDatasource:
            MovChaveSharedPreferencesDatasource by inject()

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
    fun check_return_true_and_data_returned() =
        runTest {
            val resultGetBefore = movChaveSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isFailure,
                true
            )
            assertEquals(
                resultGetBefore.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveSharedPreferencesDatasource.get"
            )
            assertEquals(
                resultGetBefore.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
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
            val resultGetAfter = movChaveSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val entityAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                entityAfter.tipoMovChave,
                TypeMovKey.REMOVE
            )
        }

}