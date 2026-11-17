package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.destino

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.utils.UiStatusState

const val TAG_DESTINO_TEXT_FIELD_PROPRIO = "tag_destino_text_field_proprio"

@Composable
fun DestinoProprioScreen(
    viewModel: DestinoProprioViewModel = hiltViewModel(),
    onNavPassagList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavNotaFiscal: () -> Unit,
    onNavObserv: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            DestinoProprioContent(
                flowApp = uiState.flowApp,
                text = uiState.text,
                onChanged = viewModel::onChanged,
                set = viewModel::set,
                typeMov = uiState.typeMov,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavPassagList = onNavPassagList,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                onNavNotaFiscal = onNavNotaFiscal,
                onNavObserv = onNavObserv,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DestinoProprioContent(
    flowApp: FlowApp,
    text: String,
    onChanged: (String) -> Unit,
    set: () -> Unit,
    typeMov: TypeMovEquip,
    onCloseDialog: () -> Unit,
    status: UiStatusState,
    onNavPassagList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavNotaFiscal: () -> Unit,
    onNavObserv: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_destino))
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = text,
            onValueChange = onChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(TAG_DESTINO_TEXT_FIELD_PROPRIO),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
            ),
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
                        FlowApp.ADD -> onNavPassagList()
                        FlowApp.CHANGE -> onNavDetalheMovProprio()
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
                FlowApp.ADD -> {
                    when (typeMov) {
                        TypeMovEquip.INPUT -> onNavObserv()
                        TypeMovEquip.OUTPUT -> onNavNotaFiscal()
                    }
                }
                FlowApp.CHANGE -> onNavDetalheMovProprio()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DestinoProprioPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DestinoProprioContent(
                flowApp = FlowApp.ADD,
                text = "Tabatinga",
                onChanged = {},
                set = {},
                typeMov = TypeMovEquip.INPUT,
                onCloseDialog = {},
                status = UiStatusState(),
                onNavPassagList = {},
                onNavDetalheMovProprio = {},
                onNavNotaFiscal = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DestinoProprioPagePreviewFieldEmpty() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DestinoProprioContent(
                text = "Tabatinga",
                flowApp = FlowApp.ADD,
                onChanged = {},
                set = {},
                typeMov = TypeMovEquip.INPUT,
                onCloseDialog = {},
                status = UiStatusState(),
                onNavPassagList = {},
                onNavDetalheMovProprio = {},
                onNavNotaFiscal = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DestinoProprioPagePreviewFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DestinoProprioContent(
                text = "Tabatinga",
                flowApp = FlowApp.ADD,
                onChanged = {},
                set = {},
                typeMov = TypeMovEquip.INPUT,
                onCloseDialog = {},
                status = UiStatusState(),
                onNavPassagList = {},
                onNavDetalheMovProprio = {},
                onNavNotaFiscal = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}