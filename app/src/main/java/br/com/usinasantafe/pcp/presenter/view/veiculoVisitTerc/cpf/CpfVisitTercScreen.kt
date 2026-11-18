package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.cpf

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
import androidx.compose.ui.platform.testTag
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
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogProgressDesign
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.presenter.view.initial.matricvigia.TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.presenter.theme.MsgUpdate
import br.com.usinasantafe.pcp.presenter.theme.Progress
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate

@Composable
fun CpfVisitTercScreen(
    viewModel: CpfVisitTercViewModel = hiltViewModel(),
    onNavTipo: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavNome: (String) -> Unit,
    onNavPassagList: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            CpfVisitTercContent(
                flowApp = uiState.flowApp,
                typeOcupante = uiState.typeOcupante,
                title = uiState.title,
                text = uiState.text,
                onCloseDialog = viewModel::onCloseDialog,
                onTextField = viewModel::onTextField,
                status = uiState.status,
                onNavTipo = onNavTipo,
                onNavDetalhe = onNavDetalhe,
                onNavNome = onNavNome,
                onNavPassagList = onNavPassagList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun CpfVisitTercContent(
    flowApp: FlowApp,
    typeOcupante: TypeOcupante,
    title: String,
    text: String,
    onTextField: (String, TypeButton) -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusStateUpdate,
    onNavTipo: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavNome: (String) -> Unit,
    onNavPassagList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = title)
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Previous
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 28.sp
            ),
            readOnly = true,
            value = text,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(setActionButton = onTextField)
        BackHandler {
            when(typeOcupante) {
                TypeOcupante.MOTORISTA -> {
                    when (flowApp) {
                        FlowApp.ADD -> onNavTipo()
                        FlowApp.CHANGE -> onNavDetalhe()
                    }
                }
                TypeOcupante.PASSAGEIRO -> onNavPassagList()
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
            onNavNome(text)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CpfVisitTercPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CpfVisitTercContent(
                typeOcupante = TypeOcupante.MOTORISTA,
                flowApp = FlowApp.ADD,
                title = "CPF TERCEIRO",
                text = "123.456.789-00",
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavTipo = {},
                onNavDetalhe = {},
                onNavNome = {},
                onNavPassagList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}