package br.com.usinasantafe.pcp.presenter.proprio.movlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class MovEquipProprioScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal() {
        setContent()
        composeTestRule.onNodeWithText("CONTROLE VEÍCULO USINA").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. Failure Usecase -> RecoverHeader -> java.lang.NullPointerException")
    }

    @Test
    fun check_return_nomeColab_if_have_vigia_in_table() = runTest {
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        val colabRoomDatasource: ColabRoomDatasource by inject()
        val localRoomDatasource: LocalRoomDatasource by inject()
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
                    descrLocal = "1 - USINAS"
                )
            )
        )
        setContent()
        composeTestRule.onNodeWithText("CONTROLE VEÍCULO USINA").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("LOCAL: 1 - USINAS").assertIsDisplayed()
    }

    @Test
    fun check_return_list_mov_if_have_mov_open() = runTest {
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        val colabRoomDatasource: ColabRoomDatasource by inject()
        val localRoomDatasource: LocalRoomDatasource by inject()
        val movEquipProprioDao: MovEquipProprioDao by inject()
        val equipDao: EquipDao by inject()
        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 1,
                    nroEquip = 100,
                    descrEquip = "teste"
                )
            )
        )
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
                notaFiscalMovEquipProprio = null,
                observMovEquipProprio = null,
            )
        )
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
                    descrLocal = "1 - USINAS"
                )
            )
        )
        setContent()
        composeTestRule.onNodeWithText("CONTROLE VEÍCULO USINA").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("LOCAL: 1 - USINAS").assertIsDisplayed()
    }

    private fun setContent() {
        composeTestRule.setContent {
            MovEquipProprioListScreen(
                viewModel = koinViewModel<MovEquipProprioListViewModel>(),
                onNavNroEquip = {},
                onNavDetalhe = {},
                onNavMenuApont = {},
                onNavSplashScreen = {},
            )
        }
    }
}