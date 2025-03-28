package br.com.usinasantafe.pcp.presenter.proprio.nroequip

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.usecases.common.CheckNroEquip
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetNroEquipProprio
import br.com.usinasantafe.pcp.domain.usecases.proprio.SetIdEquipProprio
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateEquip
import br.com.usinasantafe.pcp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.ui.theme.BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeEquip
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class NroEquipScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val checkNroEquip: CheckNroEquip by inject()
    val setIdEquipProprio: SetIdEquipProprio by inject()
    val getNroEquipProprio: GetNroEquipProprio by inject()
    val updateEquip: UpdateEquip by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun test_initial_click_button_add_char() {
        setContent()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun test_initial_click_button_clear_char() {
        setContent()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun check_show_msg_when_click_button_if_field_is_empty() {
        setContent()
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"NUMERO DO VEICULO\" PARA DAR CONTINUIDADE AO APONTAMENTO.")
    }

    @Test
    fun check_return_failure_when_matric_is_invalid() {
        setContent()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("DADO INVÁLIDO! POR FAVOR, VERIFIQUE SE O CAMPO \"NUMERO DO VEICULO\" FOI DIGITADO CORRETAMENTE OU ATUALIZE OS DADOS PARA VERIFICAR SE OS MESMOS NÃO ESTÃO DESATUALIZADOS.")
    }

    @Test
    fun check_return_failure_if_token_is_invalid() {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("{ error : Authorization header is missing }"))
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. Failure Usecase -> RecoverEquipServer -> java.lang.NullPointerException")
    }

    @Test
    fun check_return_failure_if_have_row_repeated() = runTest {
        val resultColabRetrofit = """
            [
                {"idEquip":10,"nroEquip":100},
                {"idEquip":10,"nroEquip":100}
            ]
        """.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultColabRetrofit))
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.save(
            Config(
                password = "12345",
                number = 16997417840,
                version = "6.00",
                idBD = 1
            )
        )
        setContent()
        composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. Failure Datasource -> EquipRoomDatasourceImpl.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_equip.idEquip (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")
    }

    @Test
    fun check_return_success_in_update_data() = runTest {
        val resultEquipRetrofit = """
            [
                {"idEquip":10,"nroEquip":100},
                {"idEquip":20,"nroEquip":200}
            ]
        """.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultEquipRetrofit))
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.save(
            Config(
                password = "12345",
                number = 16997417840,
                version = "6.00",
                idBD = 1
            )
        )
        setContent()
        composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("Atualização de dados realizado com sucesso!")
        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsNotDisplayed()
    }

    @Test
    fun check_return_failure_if_not_have_mov_equip() {
        setContent(
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULO,
            id = 1
        )
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. Failure Repository -> MovEquipProprioRepositoryImpl.get -> java.lang.NullPointerException")
    }

    @Test
    fun check_return_failure_if_nro_is_invalid() = runTest {
        val movEquipProprioDao: MovEquipProprioDao by inject()
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        setContent(
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULO,
            id = 1
        )
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. Failure Repository -> EquipRepositoryImpl.getNro -> java.lang.Exception: Nro is 0")
    }

    @Test
    fun check_return_nroEquip_if_getNro_execute_with_success() = runTest {
        val movEquipProprioDao: MovEquipProprioDao by inject()
        val equipDao: EquipDao by inject()
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100,
                    descrEquip = ""
                )
            )
        )
        setContent(
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULO,
            id = 1
        )
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("100").performClick()
    }

    private fun setContent(
        flowApp: FlowApp = FlowApp.ADD,
        typeEquip: TypeEquip = TypeEquip.VEICULO,
        id: Int = 0
    ) {
        composeTestRule.setContent {
            NroEquipScreen(
                viewModel = NroEquipProprioViewModel(
                    SavedStateHandle(
                        mapOf(
                            Args.FLOW_APP_ARGS to flowApp.ordinal,
                            Args.TYPE_EQUIP_ARGS to typeEquip.ordinal,
                            Args.ID_ARGS to id
                        )
                    ),
                    checkNroEquip,
                    setIdEquipProprio,
                    updateEquip,
                    getNroEquipProprio
                ),
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {}
            )
        }
    }
}