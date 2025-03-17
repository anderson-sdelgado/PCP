package br.com.usinasantafe.pcp.presenter.initial.menuapont

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.ui.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcp.ui.theme.TextSmallDesign
import br.com.usinasantafe.pcp.ui.theme.TitleDesign
import br.com.usinasantafe.pcp.utils.StatusSend

@Composable
fun MenuApontScreen(
    viewModel: MenuApontViewModel,
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
            MenuApontContent(
                flows = uiState.flows,
                statusSend = uiState.statusSend,
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                flagDialogCheck = uiState.flagDialogCheck,
                setDialogCheck = viewModel::setDialogCheck,
                closeAllMov = viewModel::closeAllMovOpen,
                flagReturn = uiState.flagReturn,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                failureStatus = uiState.failureStatus,
                onNavMovVeicProprio = onNavMovVeicProprio,
                onNavMovVeicVisitTerc = onNavMovVeicVisitTerc,
                onNavMovVeicResidencia = onNavMovVeicResidencia,
                onNavMovChave = onNavMovChave,
                onNavMovChaveEquip = onNavMovChaveEquip,
                onNavSplashScreen = onNavSplashScreen,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.returnHeader()
            viewModel.recoverStatusSend()
            viewModel.flowList()
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
    setDialogCheck: (Boolean) -> Unit,
    closeAllMov: () -> Unit,
    flagReturn: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    failureStatus: String,
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
            text =
            if(failureStatus.isEmpty()) {
                when (statusSend) {
                    StatusSend.STARTED -> stringResource(id = R.string.text_status_started)
                    StatusSend.SEND -> stringResource(id = R.string.text_status_send)
                    StatusSend.SENDING -> stringResource(id = R.string.text_status_sending)
                    StatusSend.SENT -> stringResource(id = R.string.text_status_sent)
                }
            } else {
                "Failure: $failureStatus"
            }
            ,
            fontSize = 22.sp,
            color =
            if(failureStatus.isEmpty()) {
                when (statusSend) {
                    StatusSend.STARTED -> Color.Red
                    StatusSend.SEND -> Color.Red
                    StatusSend.SENDING -> Color.Yellow
                    StatusSend.SENT -> Color.Green
                }
            } else {
                Color.Red
            }
            ,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = { setDialogCheck(true) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_pattern_out
                )
            )
        }
        BackHandler {}

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagDialogCheck){
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_return),
                setCloseDialog = { setDialogCheck(false)  },
                setActionButtonOK = { closeAllMov() }
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
                setDialogCheck = {},
                closeAllMov = {},
                flagReturn = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                failureStatus = "",
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
                setDialogCheck = {},
                closeAllMov = {},
                flagReturn = false,
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure Datasource",
                failureStatus = "",
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
                setDialogCheck = {},
                closeAllMov = {},
                flagReturn = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "Failure Datasource",
                failureStatus = "",
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