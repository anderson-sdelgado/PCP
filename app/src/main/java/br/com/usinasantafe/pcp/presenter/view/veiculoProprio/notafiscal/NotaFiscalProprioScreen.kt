package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.notafiscal

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
import br.com.usinasantafe.pcp.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.utils.UiStatusState

const val TAG_NOTA_FISCAL_TEXT_FIELD_PROPRIO = "tag_nota_fiscal_text_field_proprio"

@Composable
fun NotaFiscalProprioScreen(
    viewModel: NotaFiscalViewModel = hiltViewModel(),
    onNavDestino: () -> Unit,
    onNavObserv: () -> Unit,
    onNavDetalhe: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            NotaFiscalProprioContent(
                text = uiState.text,
                flowApp = uiState.flowApp,
                onTextField = viewModel::onTextField,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavDestino = onNavDestino,
                onNavObserv = onNavObserv,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )

        }
    }
}

@Composable
fun NotaFiscalProprioContent(
    text: String?,
    flowApp: FlowApp,
    onTextField: (String, TypeButton) -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusState,
    onNavDestino: () -> Unit,
    onNavObserv: () -> Unit,
    onNavDetalhe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_nota_fiscal))
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
            value = if(text.isNullOrEmpty()) "" else text,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NOTA_FISCAL_TEXT_FIELD_PROPRIO)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = onTextField,
            flagUpdate = false
        )
        BackHandler {
            when (flowApp) {
                FlowApp.ADD -> onNavDestino()
                FlowApp.CHANGE -> onNavDetalhe()
            }
        }

        if(status.flagDialog) {
            MsgErrors(status.errors, onCloseDialog, status.failure)
        }

    }

    LaunchedEffect(status.flagAccess) {
        if (status.flagAccess) {
            when (flowApp) {
                FlowApp.ADD -> onNavObserv()
                FlowApp.CHANGE -> onNavDetalhe()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NotaFiscalProprioPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NotaFiscalProprioContent(
                text = "",
                flowApp = FlowApp.ADD,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusState(),
                onNavDestino = {},
                onNavObserv = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotaFiscalProprioPagePreviewWithValue() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NotaFiscalProprioContent(
                text = "123456",
                flowApp = FlowApp.ADD,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusState(),
                onNavDestino = {},
                onNavObserv = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NotaFiscalProprioPagePreviewWithFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NotaFiscalProprioContent(
                text = "123456",
                flowApp = FlowApp.ADD,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusState(),
                onNavDestino = {},
                onNavObserv = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}