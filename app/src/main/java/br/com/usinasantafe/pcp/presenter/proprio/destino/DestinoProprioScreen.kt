package br.com.usinasantafe.pcp.presenter.proprio.destino

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcp.ui.theme.TitleDesign
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovEquip

const val TAG_DESTINO_TEXT_FIELD_PROPRIO = "tag_destino_text_field_proprio"

@Composable
fun DestinoProprioScreen(
    viewModel: DestinoProprioViewModel,
    onNavPassagList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavNotaFiscal: () -> Unit,
    onNavObserv: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DestinoProprioContent(
                flowApp = uiState.flowApp,
                destino = uiState.destino,
                onDestinoChanged = viewModel::onDestinoChanged,
                setDestino = viewModel::setDestino,
                typeMov = uiState.typeMov,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavPassagList = onNavPassagList,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                onNavNotaFiscal = onNavNotaFiscal,
                onNavObserv = onNavObserv,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.getDestino()
        }
    }
}

@Composable
fun DestinoProprioContent(
    flowApp: FlowApp,
    destino: String,
    onDestinoChanged: (String) -> Unit,
    setDestino: () -> Unit,
    typeMov: TypeMovEquip,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
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
            value = destino,
            onValueChange = onDestinoChanged,
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
                onClick = setDestino,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}

        if (flagDialog) {
            val text = if (failure.isEmpty()) {
                stringResource(
                    R.string.text_field_empty,
                    stringResource(id = R.string.text_title_destino)
                )
            } else {
                stringResource(id = R.string.text_failure, failure)
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagAccess) {
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
                destino = "Tabatinga",
                onDestinoChanged = {},
                setDestino = {},
                typeMov = TypeMovEquip.INPUT,
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
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
                destino = "Tabatinga",
                flowApp = FlowApp.ADD,
                onDestinoChanged = {},
                setDestino = {},
                typeMov = TypeMovEquip.INPUT,
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                failure = "",
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
                destino = "Tabatinga",
                flowApp = FlowApp.ADD,
                onDestinoChanged = {},
                setDestino = {},
                typeMov = TypeMovEquip.INPUT,
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure Usecase",
                onNavPassagList = {},
                onNavDetalheMovProprio = {},
                onNavNotaFiscal = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}