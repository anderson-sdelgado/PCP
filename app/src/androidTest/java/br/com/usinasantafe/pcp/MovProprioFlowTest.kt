package br.com.usinasantafe.pcp

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveColab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveEquip
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveLocal
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveRLocalFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveTerceiro
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveVisitante
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.presenter.MainActivity
import br.com.usinasantafe.pcp.presenter.proprio.destino.TAG_DESTINO_TEXT_FIELD_PROPRIO
import br.com.usinasantafe.pcp.presenter.proprio.observ.TAG_OBSERV_TEXT_FIELD_PROPRIO
import br.com.usinasantafe.pcp.ui.theme.TAG_BUTTON_YES_ALERT_DIALOG_CHECK
import br.com.usinasantafe.pcp.utils.FlagUpdate
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.VERSION
import br.com.usinasantafe.pcp.utils.dispatcherSuccessFunctional
import br.com.usinasantafe.pcp.utils.returnDataServerColab
import br.com.usinasantafe.pcp.utils.returnDataServerEquip
import br.com.usinasantafe.pcp.utils.returnDataServerFluxo
import br.com.usinasantafe.pcp.utils.returnDataServerLocal
import br.com.usinasantafe.pcp.utils.returnDataServerRLocalFluxo
import br.com.usinasantafe.pcp.utils.returnDataServerTerceiro
import br.com.usinasantafe.pcp.utils.returnDataServerVisitante
import br.com.usinasantafe.pcp.utils.waitUntilTimeout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Date

class MovProprioFlowTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mov_proprio_flow() = runTest {

        val server = MockWebServer()
        server.start()
        server.dispatcher =
            dispatcherSuccessFunctional
        loadKoinModules(
            generateTestAppComponent(
                server.url("").toString()
            )
        )

        initialRegister()

        Log.d("TestDebug", "Position 1")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MENU").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOV. VEÍCULO PRÓPRIO").performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE VEÍCULO USINA").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("ENTRADA").performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("ENTRADA").performClick()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 10")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("INSERIR").performClick()

        Log.d("TestDebug", "Position 11")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 12")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("INSERIR").performClick()

        Log.d("TestDebug", "Position 13")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 14")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 15")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 16")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 17")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 18")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 19")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 20")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 21")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 22")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 23")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 24")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("INSERIR").performClick()

        Log.d("TestDebug", "Position 25")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 26")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("INSERIR").performClick()

        Log.d("TestDebug", "Position 27")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 28")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 29")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 30")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("JOSE DONIZETE ALVES DA SILVA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 31")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("19035 - JOSE DONIZETE ALVES DA SILVA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 32")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 33")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 34")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("DESTINO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_DESTINO_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Destino")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 35")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 36")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("DESTINO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_DESTINO_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Destino")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 37")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Observação Entrada")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 38")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("SAÍDA").performClick()

        Log.d("TestDebug", "Position 39")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 40")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("INSERIR").performClick()

        Log.d("TestDebug", "Position 41")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 42")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 43")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 44")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("RONALDO GOMES CARLOS").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 45")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("INSERIR").performClick()

        Log.d("TestDebug", "Position 46")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 47")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("JOSE DONIZETE ALVES DA SILVA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 48")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("19035 - JOSE DONIZETE ALVES DA SILVA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 49")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("DESTINO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_DESTINO_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Destino")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 50")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 51")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("DESTINO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_DESTINO_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Destino")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 52")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 53")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 54")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 55")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Observação Saída")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 56")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_2").performClick()

        Log.d("TestDebug", "Position 57")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 58")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 59")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 60")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()

        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 61")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_2").performClick()

        Log.d("TestDebug", "Position 62")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 63")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_2").performClick()

        Log.d("TestDebug", "Position 64")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("INSERIR").performClick()

        Log.d("TestDebug", "Position 65")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 66")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 67")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_2").performClick()

        Log.d("TestDebug", "Position 68")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_2").performClick()

        Log.d("TestDebug", "Position 69")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        Log.d("TestDebug", "Position 70")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 71")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_3").performClick()

        Log.d("TestDebug", "Position 72")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 72")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_3").performClick()

        Log.d("TestDebug", "Position 73")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 74")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("RONALDO GOMES CARLOS").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 75")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_4").performClick()

        Log.d("TestDebug", "Position 76")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 77")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_4").performClick()

        Log.d("TestDebug", "Position 78")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("INSERIR").performClick()

        Log.d("TestDebug", "Position 79")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 80")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 81")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 82")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_4").performClick()

        Log.d("TestDebug", "Position 83")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_19035").performClick()

        Log.d("TestDebug", "Position 84")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        Log.d("TestDebug", "Position 85")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 86")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_5").performClick()

        Log.d("TestDebug", "Position 87")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 88")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_5").performClick()

        Log.d("TestDebug", "Position 89")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("DESTINO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_DESTINO_TEXT_FIELD_PROPRIO).performTextClearance()
        composeTestRule.onNodeWithTag(TAG_DESTINO_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Destino Alterar")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 90")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_6").performClick()

        Log.d("TestDebug", "Position 91")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 92")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_6").performClick()

        Log.d("TestDebug", "Position 93")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 94")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_7").performClick()

        Log.d("TestDebug", "Position 95")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 96")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_7").performClick()

        Log.d("TestDebug", "Position 97")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_PROPRIO).performTextClearance()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Observação Alteração")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 98")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithText("FECHAR").performClick()

        Log.d("TestDebug", "Position 99")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        Log.d("TestDebug", "Position 100")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("FECHAR MOVIMENTO(S)").performClick()

        Log.d("TestDebug", "Position 101")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        Log.d("TestDebug", "Position 102")

        composeTestRule.waitUntilTimeout(10_000)

        Log.d("TestDebug", "Position Finish")

    }

    private suspend fun initialRegister() {

        val gson = Gson()

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.save(
            Config(
                password = "12345",
                number = 16997417840,
                version = VERSION,
                idBD = 1,
                flagUpdate = FlagUpdate.UPDATED,
                matricVigia = 19759,
                idLocal = 1,
            )
        )

        val saveColab: SaveColab by inject()
        val itemTypeColab = object : TypeToken<List<Colab>>() {}.type
        val colabList = gson.fromJson<List<Colab>>(returnDataServerColab, itemTypeColab)
        saveColab(colabList)

        val saveEquip: SaveEquip by inject()
        val itemTypeEquip = object : TypeToken<List<Equip>>() {}.type
        val equipList = gson.fromJson<List<Equip>>(returnDataServerEquip, itemTypeEquip)
        saveEquip(equipList)

        val saveFluxo: SaveFluxo by inject()
        val itemTypeFluxo = object : TypeToken<List<Fluxo>>() {}.type
        val fluxoList = gson.fromJson<List<Fluxo>>(returnDataServerFluxo, itemTypeFluxo)
        saveFluxo(fluxoList)

        val saveLocal: SaveLocal by inject()
        val itemTypeLocal = object : TypeToken<List<Local>>() {}.type
        val localList = gson.fromJson<List<Local>>(returnDataServerLocal, itemTypeLocal)
        saveLocal(localList)

        val saveRLocalFluxo: SaveRLocalFluxo by inject()
        val itemTypeRLocalFluxo = object : TypeToken<List<RLocalFluxo>>() {}.type
        val rLocalFluxoList = gson.fromJson<List<RLocalFluxo>>(returnDataServerRLocalFluxo, itemTypeRLocalFluxo)
        saveRLocalFluxo(rLocalFluxoList)

        val saveTerceiro: SaveTerceiro by inject()
        val itemTypeTerceiro = object : TypeToken<List<Terceiro>>() {}.type
        val terceiroList = gson.fromJson<List<Terceiro>>(returnDataServerTerceiro, itemTypeTerceiro)
        saveTerceiro(terceiroList)

        val saveVisitante: SaveVisitante by inject()
        val itemTypeVisitante = object : TypeToken<List<Visitante>>() {}.type
        val visitanteList =
            gson.fromJson<List<Visitante>>(returnDataServerVisitante, itemTypeVisitante)
        saveVisitante(visitanteList)

        val movEquipProprioDao: MovEquipProprioDao by inject()
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date().time,
                idEquipMovEquipProprio = 300,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SENT
            )
        )

    }

}

