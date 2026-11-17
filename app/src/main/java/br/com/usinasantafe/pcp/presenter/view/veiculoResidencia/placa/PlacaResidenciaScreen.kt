package br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.placa

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.utils.UiStatusState

const val TAG_PLACA_TEXT_FIELD_RESIDENCIA = "tag_placa_text_field_residencia"

@Composable
fun PlacaResidenciaScreen(
    viewModel: PlacaResidenciaViewModel = hiltViewModel(),
    onNavVeiculo: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavMotorista: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            PlacaResidenciaContent(
                flowApp = uiState.flowApp,
                text = uiState.text,
                onChanged = viewModel::onChanged,
                set = viewModel::set,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavVeiculo = onNavVeiculo,
                onNavDetalhe = onNavDetalhe,
                onNavMotorista = onNavMotorista,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun PlacaResidenciaContent(
    flowApp: FlowApp,
    text: String,
    onChanged: (String) -> Unit,
    set: () -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusState,
    onNavVeiculo: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavMotorista: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_placa))
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = text,
            onValueChange = onChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(TAG_PLACA_TEXT_FIELD_RESIDENCIA),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 28.sp
            ),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    when (flowApp) {
                        FlowApp.ADD -> onNavVeiculo()
                        FlowApp.CHANGE -> onNavDetalhe()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = set,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}

        if(status.flagDialog) {
            MsgErrors(status.errors, onCloseDialog, status.failure)
        }

    }

    LaunchedEffect(status.flagAccess) {
        if (status.flagAccess) {
            when (flowApp) {
                FlowApp.ADD -> onNavMotorista()
                FlowApp.CHANGE -> onNavDetalhe()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PlacaResidenciaPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlacaResidenciaContent(
                flowApp = FlowApp.ADD,
                text = "Tabatinga",
                onChanged = {},
                set = {},
                onCloseDialog = {},
                status = UiStatusState(),
                onNavVeiculo = {},
                onNavDetalhe = {},
                onNavMotorista = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}