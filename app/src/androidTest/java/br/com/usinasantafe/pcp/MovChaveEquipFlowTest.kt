package br.com.usinasantafe.pcp

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
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
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.presenter.MainActivity
import br.com.usinasantafe.pcp.presenter.chave.observ.TAG_OBSERV_TEXT_FIELD_CHAVE
import br.com.usinasantafe.pcp.ui.theme.TAG_BUTTON_YES_ALERT_DIALOG_CHECK
import br.com.usinasantafe.pcp.utils.FlagUpdate
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
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
import java.util.Date

class MovChaveEquipFlowTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun main_flow() = runTest {

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

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE DE EQUIPAMENTO").performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE DE EQUIPAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("RECEBIMENTO DE CHAVE").performClick()

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE DE EQUIPAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("RECEBIMENTO DE CHAVE").performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NUMERO DO EQUIPAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("6").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("6").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NUMERO DO EQUIPAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("6").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("6").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MATRICULA COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 10")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 11")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MATRICULA COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 12")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 13")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 14")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MATRICULA COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 15")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 16")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_CHAVE).performTextInput("TESTE RECEBIMENTO")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 17")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE DE EQUIPAMENTO").performClick()
        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 18")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 19")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE DE EQUIPAMENTO").performClick()
        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 20")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MATRICULA COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 21")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("RONALDO GOMES CARLOS").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 22")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_CHAVE).performTextInput("TESTE ENTREGA")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 23")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE DE EQUIPAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("EDITAR MOVIMENTO(S)").performClick()

        Log.d("TestDebug", "Position 24")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR").performClick()

        Log.d("TestDebug", "Position 25")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE DE EQUIPAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("EDITAR MOVIMENTO(S)").performClick()

        Log.d("TestDebug", "Position 26")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 27")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR").performClick()

        Log.d("TestDebug", "Position 28")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 29")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 30")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 31")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 32")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MATRICULA COLABORADOR").assertIsDisplayed()
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

        Log.d("TestDebug", "Position 33")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("RONALDO GOMES CARLOS").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 34")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithTag("item_list_2").performClick()

        Log.d("TestDebug", "Position 35")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 36")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithTag("item_list_2").performClick()

        Log.d("TestDebug", "Position 37")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_CHAVE).performTextClearance()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_CHAVE).performTextInput("TESTE ALTERAÇÃO")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 38")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithText("FECHAR").performClick()

        Log.d("TestDebug", "Position 39")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        Log.d("TestDebug", "Position 40")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("FECHAR MOVIMENTO(S)").performClick()

        Log.d("TestDebug", "Position 41")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        Log.d("TestDebug", "Position 42")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("EDITAR MOVIMENTO(S)").performClick()

        Log.d("TestDebug", "Position 43")

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
                matricVigia = 19759,
                idLocal = 4,
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