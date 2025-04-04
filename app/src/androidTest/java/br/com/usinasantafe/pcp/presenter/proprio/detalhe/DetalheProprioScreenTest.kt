package br.com.usinasantafe.pcp.presenter.proprio.detalhe

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.usecases.proprio.CloseMovProprio
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetDetalheProprio
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class DetalheProprioScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val getDetalheProprio: GetDetalheProprio by inject()
    private val closeMovProprio: CloseMovProprio by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()
    private val colabRepository: ColabRepository by inject()
    private val equipRepository: EquipRepository by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_mov() = runTest {
        setContent()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. Failure Repository -> MovEquipProprioRepositoryImpl.get -> java.lang.NullPointerException")
    }


    @Test
    fun check_return_model_if_recover_detalhe_proprio_execute_with_success() = runTest {
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
        colabRepository.addAll(
            listOf(
                Colab(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                Colab(
                    matricColab = 19035,
                    nomeColab = "JOSE DONIZETE"
                )
            )
        )
        equipRepository.addAll(
            listOf(
                Equip(
                    idEquip = 10,
                    nroEquip = 100,
                    descrEquip = "teste"
                ),
                Equip(
                    idEquip = 100,
                    nroEquip = 200,
                    descrEquip = "teste"
                )
            )
        )
        setContent()
        composeTestRule.waitUntilTimeout(5_000)
        composeTestRule.onNodeWithText("DATA/HORA: 09/08/2024 11:21").assertIsDisplayed()
        composeTestRule.onNodeWithText("OBSERVAÇÕES: TESTE OBSERV").assertIsDisplayed()
    }

    private fun setContent() {
        composeTestRule.setContent {
            DetalheMovProprioScreen(
                viewModel = DetalheProprioViewModel(
                    SavedStateHandle(
                        mapOf(
                            Args.ID_ARGS to 1
                        )
                    ),
                    getDetalheProprio,
                    closeMovProprio
                ),
                onNavMovProprioList = {},
                onNavNroEquip = {},
                onNavEquipSegList = {},
                onNavMatricColab = {},
                onNavPassagList = {},
                onNavDestino = {},
                onNavNotaFiscal = {},
                onNavObserv = {},
            )
        }
    }
}