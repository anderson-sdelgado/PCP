package br.com.usinasantafe.pcp

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.pcp.domain.entities.stable.Chave
import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveChave
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveColab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveEquip
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveLocal
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveLocalTrab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveRLocalFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveTerceiro
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveVisitante
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.presenter.MainActivity
import br.com.usinasantafe.pcp.ui.theme.TAG_BUTTON_YES_ALERT_DIALOG_CHECK
import br.com.usinasantafe.pcp.utils.FlagUpdate
import br.com.usinasantafe.pcp.utils.VERSION
import br.com.usinasantafe.pcp.utils.dispatcherSuccessFunctional
import br.com.usinasantafe.pcp.utils.returnDataServerChave
import br.com.usinasantafe.pcp.utils.returnDataServerColab
import br.com.usinasantafe.pcp.utils.returnDataServerEquip
import br.com.usinasantafe.pcp.utils.returnDataServerFluxo
import br.com.usinasantafe.pcp.utils.returnDataServerLocal
import br.com.usinasantafe.pcp.utils.returnDataServerLocalTrab
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

class InitialFlowTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun initial_usina_flow_veic_proprio() = runTest {

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

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR")
            .performClick()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME VIGIA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 10")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME VIGIA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 11")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("LOCAL").assertIsDisplayed()
        composeTestRule.onNodeWithText("1 - USINA").performClick()

        Log.d("TestDebug", "Position 12")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MENU").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 13")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOV. VEÍCULO PRÓPRIO")
            .performClick()

        Log.d("TestDebug", "Position 14")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE VEÍCULO USINA").assertIsDisplayed()

        Log.d("TestDebug", "Position 15")

        composeTestRule.waitUntilTimeout(10_000)

        Log.d("TestDebug", "Position Finish")

    }

    @Test
    fun initial_usina_flow_veic_visit_terc() = runTest {

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

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME VIGIA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("LOCAL").assertIsDisplayed()
        composeTestRule.onNodeWithText("2 - MOTO").performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MENU").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOV. VEÍCULO VISITANTE/TERCEIRO")
            .performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE VEÍCULO VISITANTE/TERCEIRO").assertIsDisplayed()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 10")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("SAIR")
            .performClick()

        Log.d("TestDebug", "Position 11")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        Log.d("TestDebug", "Position 12")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        Log.d("TestDebug", "Position 13")

        composeTestRule.waitUntilTimeout(10_000)

        Log.d("TestDebug", "Position Finish")

    }

    @Test
    fun initial_usina_flow_veic_residencia() = runTest {

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

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME VIGIA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("LOCAL").assertIsDisplayed()
        composeTestRule.onNodeWithText("3 - RESIDÊNCIA").performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MENU").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOV. VEÍCULO RESIDÊNCIA")
            .performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE VEÍCULO RESIDÊNCIA").assertIsDisplayed()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(10_000)

        Log.d("TestDebug", "Position Finish")

    }

    @Test
    fun initial_base_ibitinga_flow_veic_proprio() = runTest {

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

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME VIGIA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("LOCAL").assertIsDisplayed()
        composeTestRule.onNodeWithText("4 - BASE IBITINGA").performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MENU").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOV. VEÍCULO PRÓPRIO")
            .performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE VEÍCULO USINA").assertIsDisplayed()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(10_000)

        Log.d("TestDebug", "Position Finish")

    }

    @Test
    fun initial_base_ibitinga_flow_controle_chave() = runTest {

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

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME VIGIA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("LOCAL").assertIsDisplayed()
        composeTestRule.onNodeWithText("4 - BASE IBITINGA").performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MENU").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE")
            .performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE").assertIsDisplayed()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

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
            )
        )

        val saveChave: SaveChave by inject()
        val itemTypeChave = object : TypeToken<List<Chave>>() {}.type
        val chaveList = gson.fromJson<List<Chave>>(returnDataServerChave, itemTypeChave)
        saveChave(chaveList)

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

        val saveLocalTrab: SaveLocalTrab by inject()
        val itemTypeLocalTrab = object : TypeToken<List<LocalTrab>>() {}.type
        val localTrabList = gson.fromJson<List<LocalTrab>>(returnDataServerLocalTrab, itemTypeLocalTrab)
        saveLocalTrab(localTrabList)

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

    }

}