package br.com.usinasantafe.pcp.presenter.view.initial.menuapont

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TextSmallDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.presenter.theme.colorStatus
import br.com.usinasantafe.pcp.presenter.theme.textStatus
import br.com.usinasantafe.pcp.utils.UiStatusState

@Composable
fun MenuApontScreen(
    viewModel: MenuApontViewModel = hiltViewModel(),
    onNavMovVeicProprio: () -> Unit,
    onNavMovVeicVisitTerc: () -> Unit,
    onNavMovVeicResidencia: () -> Unit,
    onNavMovChave: () -> Unit,
    onNavMovChaveEquip: () -> Unit,
    onNavSplashScreen: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
                viewModel.flowList()
            }

            MenuApontContent(
                flows = uiState.flows,
                statusSend = uiState.statusSend,
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                flagDialogCheck = uiState.flagDialogCheck,
                onDialogCheck = viewModel::onDialogCheck,
                closeAllMov = viewModel::onCloseAllMovOpen,
                flagReturn = uiState.flagReturn,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavMovVeicProprio = onNavMovVeicProprio,
                onNavMovVeicVisitTerc = onNavMovVeicVisitTerc,
                onNavMovVeicResidencia = onNavMovVeicResidencia,
                onNavMovChave = onNavMovChave,
                onNavMovChaveEquip = onNavMovChaveEquip,
                onNavSplashScreen = onNavSplashScreen,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MenuApontContent(
    flows: List<Fluxo>,
    statusSend: StatusSend,
    descrVigia: String,
    descrLocal: String,
    flagDialogCheck: Boolean,
    onDialogCheck: (Boolean) -> Unit,
    closeAllMov: () -> Unit,
    flagReturn: Boolean,
    onCloseDialog: () -> Unit,
    status: UiStatusState,
    onNavMovVeicProprio: () -> Unit,
    onNavMovVeicVisitTerc: () -> Unit,
    onNavMovVeicResidencia: () -> Unit,
    onNavMovChave: () -> Unit,
    onNavMovChaveEquip: () -> Unit,
    onNavSplashScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TextSmallDesign(text = "VIGIA: $descrVigia")
        TextSmallDesign(text = "LOCAL: $descrLocal")
        TitleDesign(text = "MENU")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(flows) { flow ->
                ItemListDesign(
                    text = flow.descrFluxo,
                    setActionItem = {
                        when(flow.idFluxo){
                            1 -> onNavMovVeicProprio()
                            2 -> onNavMovVeicVisitTerc()
                            3 -> onNavMovVeicResidencia()
                            4 -> onNavMovChave()
                            5 -> onNavMovChaveEquip()
                        }
                    },
                    font = 26
                )
            }
        }
        Text(
            textAlign = TextAlign.Left,
            text = textStatus(status.failure, statusSend),
            fontSize = 22.sp,
            color = colorStatus(status.failure, statusSend),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = { onDialogCheck(true) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_pattern_out
                )
            )
        }
        BackHandler {}

        if(status.flagDialog) {
            MsgErrors(status.errors, onCloseDialog, status.failure)
        }

        if(flagDialogCheck){
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_return),
                setCloseDialog = { onDialogCheck(false)  },
                setActionButtonYes = { closeAllMov() }
            )
        }

        if(flagReturn){
            onNavSplashScreen()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MenuApontPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuApontContent(
                flows = listOf(
                    Fluxo(
                        idFluxo = 1,
                        descrFluxo = "MOV. VEÍCULO PRÓPRIO"
                    ),
                    Fluxo(
                        idFluxo = 2,
                        descrFluxo = "MOV. VEÍCULO VISITANTE/TERCEIRO"
                    ),
                    Fluxo(
                        idFluxo = 3,
                        descrFluxo = "MOV. VEÍCULO RESIDÊNCIA"
                    ),
                ),
                statusSend = StatusSend.SEND,
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                flagDialogCheck = false,
                onDialogCheck = {},
                closeAllMov = {},
                flagReturn = false,
                onCloseDialog = {},
                status = UiStatusState(
                    flagDialog = false,
                    flagFailure = false,
                    flagAccess = false,
                    failure = "",
                ),
                onNavMovVeicProprio = {},
                onNavMovVeicVisitTerc = {},
                onNavMovVeicResidencia = {},
                onNavMovChave = {},
                onNavMovChaveEquip = {},
                onNavSplashScreen = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuApontPagePreviewShowDialog() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuApontContent(
                flows = listOf(
                    Fluxo(
                        idFluxo = 1,
                        descrFluxo = "MOV. VEÍCULO PRÓPRIO"
                    ),
                    Fluxo(
                        idFluxo = 2,
                        descrFluxo = "MOV. VEÍCULO VISITANTE/TERCEIRO"
                    ),
                    Fluxo(
                        idFluxo = 3,
                        descrFluxo = "MOV. VEÍCULO RESIDÊNCIA"
                    ),
                ),
                statusSend = StatusSend.SENT,
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                flagDialogCheck = false,
                onDialogCheck = {},
                closeAllMov = {},
                flagReturn = false,
                onCloseDialog = {},
                status = UiStatusState(
                    flagDialog = true,
                    flagFailure = false,
                    flagAccess = false,
                    failure = "",
                ),
                onNavMovVeicProprio = {},
                onNavMovVeicVisitTerc = {},
                onNavMovVeicResidencia = {},
                onNavMovChave = {},
                onNavMovChaveEquip = {},
                onNavSplashScreen = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuApontPagePreviewShowDialogCheck() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuApontContent(
                flows = listOf(
                    Fluxo(
                        idFluxo = 1,
                        descrFluxo = "MOV. VEÍCULO PRÓPRIO"
                    ),
                    Fluxo(
                        idFluxo = 2,
                        descrFluxo = "MOV. VEÍCULO VISITANTE/TERCEIRO"
                    ),
                    Fluxo(
                        idFluxo = 3,
                        descrFluxo = "MOV. VEÍCULO RESIDÊNCIA"
                    ),
                ),
                statusSend = StatusSend.SEND,
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                flagDialogCheck = true,
                onDialogCheck = {},
                closeAllMov = {},
                flagReturn = false,
                onCloseDialog = {},
                status = UiStatusState(
                    flagDialog = false,
                    flagFailure = false,
                    flagAccess = false,
                    failure = "",
                ),
                onNavMovVeicProprio = {},
                onNavMovVeicVisitTerc = {},
                onNavMovVeicResidencia = {},
                onNavMovChave = {},
                onNavMovChaveEquip = {},
                onNavSplashScreen = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}