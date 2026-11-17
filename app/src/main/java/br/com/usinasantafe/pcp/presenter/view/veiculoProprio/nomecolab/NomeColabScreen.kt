package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.nomecolab

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.utils.UiStatusState

@Composable
fun NomeColabScreen(
    viewModel: NomeColabViewModel = hiltViewModel(),
    onNavMatricColab: () -> Unit,
    onNavPassagColabList: () -> Unit,
    onNavDetalhe: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            NomeColabContent(
                flowApp = uiState.flowApp,
                typeOcupante = uiState.typeOcupante,
                nomeColab = uiState.nomeColab,
                set = viewModel::set,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavMatricColab = onNavMatricColab,
                onNavPassagColabList = onNavPassagColabList,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun NomeColabContent(
    flowApp: FlowApp,
    typeOcupante: TypeOcupante,
    nomeColab: String,
    set: () -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusState,
    onNavMatricColab: () -> Unit,
    onNavPassagColabList: () -> Unit,
    onNavDetalhe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_nome_colab
            )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = nomeColab,
                fontSize = 28.sp,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = onNavMatricColab,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_cancel)
                )
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
            when (typeOcupante) {
                TypeOcupante.MOTORISTA -> {
                    when (flowApp) {
                        FlowApp.ADD -> onNavPassagColabList()
                        FlowApp.CHANGE -> onNavDetalhe()
                    }
                }

                TypeOcupante.PASSAGEIRO -> onNavPassagColabList()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NomeColabPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NomeColabContent(
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                nomeColab = "ANDERSON DA SILVA DELGADO",
                set = {},
                onCloseDialog = {},
                status = UiStatusState(),
                onNavMatricColab = {},
                onNavPassagColabList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NomeColabPagePreviewFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NomeColabContent(
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                nomeColab = "ANDERSON DA SILVA DELGADO",
                set = {},
                onCloseDialog = {},
                status = UiStatusState(),
                onNavMatricColab = {},
                onNavPassagColabList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}