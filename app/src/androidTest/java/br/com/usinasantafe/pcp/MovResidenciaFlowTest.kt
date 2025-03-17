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
import br.com.usinasantafe.pcp.presenter.residencia.motorista.TAG_MOTORISTA_TEXT_FIELD_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.observ.TAG_OBSERV_TEXT_FIELD_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.placa.TAG_PLACA_TEXT_FIELD_RESIDENCIA
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.TAG_VEICULO_TEXT_FIELD_RESIDENCIA
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

class MovResidenciaFlowTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mov_residencia_flow() = runTest {

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

        composeTestRule.onNodeWithText("MOV. VEÍCULO RESIDÊNCIA").performClick()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("ENTRADA").performClick()

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("ENTRADA").performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_VEICULO_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Gol")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_VEICULO_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Gol")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PLACA").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PLACA_TEXT_FIELD_RESIDENCIA)
            .performTextInput("abc1234")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 10")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 11")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PLACA").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PLACA_TEXT_FIELD_RESIDENCIA)
            .performTextInput("abc1234")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 12")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOTORISTA").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_MOTORISTA_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Anderson")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 13")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 14")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOTORISTA").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_MOTORISTA_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Anderson")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 15")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Teste Entrada")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 16")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 17")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 18")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 19")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Teste Saída")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 20")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("EDITAR MOVIMENTO(S)").performClick()

        Log.d("TestDebug", "Position 21")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR").performClick()

        Log.d("TestDebug", "Position 22")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("EDITAR MOVIMENTO(S)").performClick()

        Log.d("TestDebug", "Position 23")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 24")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR").performClick()

        Log.d("TestDebug", "Position 25")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 26")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 27")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 28")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        Log.d("TestDebug", "Position 29")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_VEICULO_TEXT_FIELD_RESIDENCIA)
            .performTextClearance()
        composeTestRule.onNodeWithTag(TAG_VEICULO_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Gol Teste")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 30")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_2").performClick()

        Log.d("TestDebug", "Position 31")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 32")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_2").performClick()

        Log.d("TestDebug", "Position 33")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PLACA").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PLACA_TEXT_FIELD_RESIDENCIA)
            .performTextClearance()
        composeTestRule.onNodeWithTag(TAG_PLACA_TEXT_FIELD_RESIDENCIA)
            .performTextInput("alt1234")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 34")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_3").performClick()

        Log.d("TestDebug", "Position 35")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 36")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_3").performClick()

        Log.d("TestDebug", "Position 37")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOTORISTA").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_MOTORISTA_TEXT_FIELD_RESIDENCIA)
            .performTextClearance()
        composeTestRule.onNodeWithTag(TAG_MOTORISTA_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Anderson Alterado")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 38")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_4").performClick()

        Log.d("TestDebug", "Position 39")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CANCELAR").performClick()

        Log.d("TestDebug", "Position 40")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_4").performClick()

        Log.d("TestDebug", "Position 41")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_RESIDENCIA)
            .performTextClearance()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Teste Alterado")
        composeTestRule.onNodeWithText("OK").performClick()

        Log.d("TestDebug", "Position 42")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithText("FECHAR").performClick()

        Log.d("TestDebug", "Position 43")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        Log.d("TestDebug", "Position 44")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("FECHAR MOVIMENTO(S)").performClick()

        Log.d("TestDebug", "Position 45")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        Log.d("TestDebug", "Position 46")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("EDITAR MOVIMENTO(S)").performClick()

        Log.d("TestDebug", "Position 47")

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