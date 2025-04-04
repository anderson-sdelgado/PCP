package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class IGetHeaderTest : KoinTest {


    private val usecase: GetHeader by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
    private val colabRoomDatasource: ColabRoomDatasource by inject()
    private val localRoomDatasource: LocalRoomDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal() = runTest {
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Usecase -> RecoverHeader"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException"
        )
    }

    @Test
    fun check_return_success() = runTest {
        configSharedPreferences.save(
            Config(
                matricVigia = 19759,
                idLocal = 1
            )
        )
        colabRoomDatasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )
        localRoomDatasource.addAll(
            listOf(
                LocalRoomModel(
                    idLocal = 1,
                    descrLocal = "1 - USINA"
                )
            )
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        val header = result.getOrNull()!!
        assertEquals(
            header.descrVigia,
            "19759 - ANDERSON DA SILVA DELGADO"
        )
        assertEquals(
            header.descrLocal,
            "1 - USINA"
        )
    }
}