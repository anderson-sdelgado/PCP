package br.com.usinasantafe.pcp.domain.usecases.updatetable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateVisitante
import br.com.usinasantafe.pcp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class UpdateVisitanteImplTest : KoinTest {

    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
    private val usecase: UpdateVisitante by inject()
    private val visitanteDao: VisitanteDao by inject()

    @Test
    fun verify_return_data_if_success_usecase() =
        runTest {
            var pos = 0f
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody(resultVisitanteUpdate))
            loadKoinModules(generateTestAppComponent(server.url("/").toString()))
            configSharedPreferences.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "6.00",
                    idBD = 1
                )
            )
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(list.count(), 3)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            val listData = visitanteDao.listAll()
            assertEquals(
                listData.size,
                1
            )
        }
}

val resultVisitanteUpdate = """
    [{"idVisitante":1,"nomeVisitante":"Visitante","cpfVisitante":"123.456.789-00","empresaVisitante":"Empresa Visitante"}]
""".trimIndent()
