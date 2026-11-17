package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.matriccolab

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.presenter.theme.MsgUpdate
import br.com.usinasantafe.pcp.presenter.theme.Progress
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate

@Composable
fun MatricColabScreen(
    viewModel: MatricColabViewModel = hiltViewModel(),
    onNavPassagColabList: () -> Unit,
    onNavEquipSegList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavNomeColab: (String) -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            MatricColabContent(
                flowApp = uiState.flowApp,
                typeOcupante = uiState.typeOcupante,
                text = uiState.text,
                onTextField = viewModel::onTextField,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavEquipSegList = onNavEquipSegList,
                onNavPassagColabList = onNavPassagColabList,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                onNavNomeColab = onNavNomeColab,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MatricColabContent(
    flowApp: FlowApp,
    typeOcupante: TypeOcupante,
    text: String,
    onTextField: (String, TypeButton) -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusStateUpdate,
    onNavEquipSegList: () -> Unit,
    onNavPassagColabList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavNomeColab: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_matric_colab
            )
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Previous
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 28.sp,
            ),
            readOnly = true,
            value = text,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(setActionButton = onTextField)
        BackHandler {
            when (flowApp) {
                FlowApp.ADD -> {
                    when(typeOcupante){
                        TypeOcupante.MOTORISTA -> onNavEquipSegList()
                        TypeOcupante.PASSAGEIRO -> onNavPassagColabList()
                    }
                }
                FlowApp.CHANGE -> onNavDetalheMovProprio()
            }
        }

        if (status.flagDialog) {
            MsgUpdate(status = status, onClickOk = onCloseDialog, value = stringResource(id = R.string.text_title_matric_colab))
        }

        if (status.flagProgress) {
            Progress(status)
        }

    }

    LaunchedEffect(status.flagAccess) {
        if (status.flagAccess) {
            onNavNomeColab(text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                text = "19759",
                typeOcupante = TypeOcupante.MOTORISTA,
                flowApp = FlowApp.ADD,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavEquipSegList = {},
                onNavPassagColabList = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreviewFieldEmpty() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                text = "",
                typeOcupante = TypeOcupante.MOTORISTA,
                flowApp = FlowApp.ADD,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavEquipSegList = {},
                onNavPassagColabList = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreviewFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                text = "",
                typeOcupante = TypeOcupante.MOTORISTA,
                flowApp = FlowApp.ADD,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavEquipSegList = {},
                onNavPassagColabList = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreviewFailureUpdate() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                text = "",
                typeOcupante = TypeOcupante.MOTORISTA,
                flowApp = FlowApp.ADD,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavEquipSegList = {},
                onNavPassagColabList = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreviewBlockedAccess() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                text = "",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavEquipSegList = {},
                onNavPassagColabList = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreviewUpdate() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                text = "",
                typeOcupante = TypeOcupante.MOTORISTA,
                flowApp = FlowApp.ADD,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavEquipSegList = {},
                onNavPassagColabList = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}