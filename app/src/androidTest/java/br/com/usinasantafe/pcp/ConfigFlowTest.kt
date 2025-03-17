package br.com.usinasantafe.pcp

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.presenter.MainActivity
import br.com.usinasantafe.pcp.presenter.configuration.config.TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.pcp.presenter.configuration.config.TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.pcp.presenter.configuration.senha.TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN
import br.com.usinasantafe.pcp.utils.VERSION
import br.com.usinasantafe.pcp.utils.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ConfigFlowTest: KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verify_flow_configuration_initial() = runTest {

        val server = MockWebServer()
        server.start()
        server.dispatcher = dispatcherSuccess
        loadKoinModules(
            generateTestAppComponent(
                server.url("").toString()
            )
        )

        Log.d("TestDebug", "Position 1")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR")
            .performClick()

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("SENHA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR")
            .performClick()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("SENHA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS")
            .performClick()

        Log.d("TestDebug", "Position 10")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 11")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()

        Log.d("TestDebug", "Position 12")

        composeTestRule.waitUntilTimeout(3_000)

        Log.d("TestDebug", "Position Finish")

    }

    @Test
    fun verify_flow_configuration_update() = runTest {

        val server = MockWebServer()
        server.start()
        server.dispatcher = dispatcherSuccess
        loadKoinModules(generateTestAppComponent(server.url("").toString()))

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.save(
            Config(
                password = "12345",
                number = 16997417840,
                version = VERSION,
                idBD = 1
            )
        )

        composeTestRule.waitUntilTimeout(3_000)

        Log.d("TestDebug", "Position 1")

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        Log.d("TestDebug", "Position 3 ")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("SENHA").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 4}")

        composeTestRule.waitUntilTimeout(10_000)

        composeTestRule.onNodeWithText("NRO APARELHO:").assertIsDisplayed()
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS")
            .performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        Log.d("TestDebug", "Position Finish")

    }


}

val dispatcherSuccess: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody(resultTokenRetrofit)
            "/chave.php" -> return MockResponse().setBody(resultChaveRetrofit)
            "/colab.php" -> return MockResponse().setBody(resultColabRetrofit)
            "/equip.php" -> return MockResponse().setBody(resultEquipRetrofit)
            "/fluxo.php" -> return MockResponse().setBody(resultFluxoRetrofit)
            "/local.php" -> return MockResponse().setBody(resultLocalRetrofit)
            "/localtrab.php" -> return MockResponse().setBody(resultLocalTrabRetrofit)
            "/rlocalfluxo.php" -> return MockResponse().setBody(resultRLocalFluxoRetrofit)
            "/terceiro.php" -> return MockResponse().setBody(resultTerceiroRetrofit)
            "/visitante.php" -> return MockResponse().setBody(resultVisitanteRetrofit)
        }
        return MockResponse().setResponseCode(404)
    }
}

val dispatcherFailureToken: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody("""""")
            "/colab.php" -> return MockResponse().setBody(resultColabRetrofit)
            "/equip.php" -> return MockResponse().setBody(resultEquipRetrofit)
            "/local.php" -> return MockResponse().setBody(resultLocalRetrofit)
            "/terceiro.php" -> return MockResponse().setBody(resultTerceiroRetrofit)
            "/visitante.php" -> return MockResponse().setBody(resultVisitanteRetrofit)
        }
        return MockResponse().setResponseCode(404)
    }
}

val dispatcherFailureColab: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody(resultTokenRetrofit)
            "/colab.php" -> return MockResponse().setBody(resultFailureColabRetrofit)
            "/equip.php" -> return MockResponse().setBody(resultEquipRetrofit)
            "/local.php" -> return MockResponse().setBody(resultLocalRetrofit)
            "/terceiro.php" -> return MockResponse().setBody(resultTerceiroRetrofit)
            "/visitante.php" -> return MockResponse().setBody(resultVisitanteRetrofit)
        }
        return MockResponse().setResponseCode(404)
    }
}

val dispatcherFailureTerceiro: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody(resultTokenRetrofit)
            "/colab.php" -> return MockResponse().setBody(resultColabRetrofit)
            "/equip.php" -> return MockResponse().setBody(resultEquipRetrofit)
            "/local.php" -> return MockResponse().setBody(resultLocalRetrofit)
            "/terceiro.php" -> return MockResponse().setBody(resultFailureTerceiroRetrofit)
            "/visitante.php" -> return MockResponse().setBody(resultVisitanteRetrofit)
        }
        return MockResponse().setResponseCode(404)
    }
}

val resultTokenRetrofit = """{"idBD":1}""".trimIndent()

val resultChaveRetrofit = """
    [{"idChave":1,"descrChave":"01 TI","idLocalTrab":1}]
""".trimIndent()

val resultColabRetrofit = """
    [{"matricColab":19759,"nomeColab":"ANDERSON DA SILVA DELGADO"}]
""".trimIndent()

val resultFailureColabRetrofit = """
    [{"matricColab":19759a,"nomeColab":"ANDERSON DA SILVA DELGADO"}]
""".trimIndent()

val resultEquipRetrofit = """
    [{"idEquip":19,"nroEquip":190,"descrEquip": "HIDROJATO NA MOTOR ESTACIONARIO"}]
""".trimIndent()

val resultFluxoRetrofit = """
    [{"idFluxo":1,"descrFluxo":"MOV. EQUIP. PRÓPRIO"}]
""".trimIndent()

val resultLocalRetrofit = """
    [{"idLocal":1,"descrLocal":"Usina"}]
""".trimIndent()

val resultLocalTrabRetrofit = """
    [{"idLocalTrab":1,"descrLocalTrab":"TI"}]
""".trimIndent()

val resultRLocalFluxoRetrofit = """
    [{"idRLocalFluxo":1,"idFluxo":1,"idLocal":1}]
""".trimIndent()

val resultTerceiroRetrofit = """
    [{"idTerceiro":1,"idBDTerceiro":1,"cpfTerceiro":"123.456.789-00","nomeTerceiro":"Terceiro","empresaTerceiro":"Empresa Terceiro"}]
""".trimIndent()

val resultFailureTerceiroRetrofit = """
    [
        {"idTerceiro":1,"idBDTerceiro":1,"cpfTerceiro":"123.456.789-00","nomeTerceiro":"Terceiro","empresaTerceiro":"Empresa Terceiro"},
        {"idTerceiro":1,"idBDTerceiro":1,"cpfTerceiro":"123.456.789-00","nomeTerceiro":"Terceiro","empresaTerceiro":"Empresa Terceiro"}
    ]
""".trimIndent()

val resultVisitanteRetrofit = """
    [{"idVisitante":1,"cpfVisitante":"123.456.789-00","nomeVisitante":"Visitante","empresaVisitante":"Empresa Visitante"}]
""".trimIndent()