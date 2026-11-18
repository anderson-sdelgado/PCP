package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.detalhe

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.utils.UiStatusState

@Composable
fun DetalheVisitTercScreen(
    viewModel: DetalheVisitTercViewModel = hiltViewModel(),
    onNavMovEquipEditList: () -> Unit,
    onNavVeiculo: () -> Unit,
    onNavPlaca: () -> Unit,
    onNavCpf: () -> Unit,
    onNavPassagList: () -> Unit,
    onNavDestino: () -> Unit,
    onNavObserv: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            DetalheVisitTercContent(
                dthr = uiState.dthr,
                tipoMov = uiState.tipoMov,
                veiculo = uiState.veiculo,
                placa = uiState.placa,
                tipoVisitTerc = uiState.tipoVisitTerc,
                motorista = uiState.motorista,
                passageiro = uiState.passageiro,
                destino = uiState.destino,
                observ = uiState.observ,
                onCloseDialog = viewModel::onCloseDialog,
                onCloseMov = viewModel::onCloseMov,
                flagCloseMov = uiState.flagCloseMov,
                flagDialogCheck = uiState.flagDialogCheck,
                onDialogCheck = viewModel::onDialogCheck,
                status = uiState.status,
                onNavMovEquipEditList = onNavMovEquipEditList,
                onNavVeiculo = onNavVeiculo,
                onNavPlaca = onNavPlaca,
                onNavCpf = onNavCpf,
                onNavPassagList = onNavPassagList,
                onNavDestino = onNavDestino,
                onNavObserv = onNavObserv,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DetalheVisitTercContent(
    dthr: String,
    tipoMov: String,
    veiculo: String,
    placa: String,
    tipoVisitTerc: String,
    motorista: String,
    passageiro: String,
    destino: String?,
    observ: String?,
    onCloseDialog: () -> Unit,
    onCloseMov: () -> Unit,
    flagCloseMov: Boolean,
    flagDialogCheck: Boolean,
    onDialogCheck: (Boolean) -> Unit,
    status: UiStatusState,
    onNavMovEquipEditList: () -> Unit,
    onNavVeiculo: () -> Unit,
    onNavPlaca: () -> Unit,
    onNavCpf: () -> Unit,
    onNavPassagList: () -> Unit,
    onNavDestino: () -> Unit,
    onNavObserv: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_detalhe))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ItemListDesign(
                    text = "DATA/HORA: $dthr",
                    setActionItem = {}
                )
            }
            item {
                ItemListDesign(
                    text = tipoMov,
                    setActionItem = {}
                )
            }
            item {
                ItemListDesign(
                    text = "VEICULO: $veiculo",
                    setActionItem = onNavVeiculo,
                    id = 1
                )
            }
            item {
                ItemListDesign(
                    text = "PLACA: $placa",
                    setActionItem = onNavPlaca,
                    id = 2
                )
            }
            item {
                ItemListDesign(
                    text = tipoVisitTerc,
                    setActionItem = {}
                )
            }
            item {
                ItemListDesign(
                    text = "MOTORISTA: $motorista",
                    setActionItem = onNavCpf,
                    id = 3
                )
            }
            item {
                ItemListDesign(
                    text = "PASSAGEIRO(S): $passageiro",
                    setActionItem = onNavPassagList,
                    id = 4
                )
            }
            item {
                ItemListDesign(
                    text = "DESTINO: ${if(destino.isNullOrEmpty()) "" else destino}" ,
                    setActionItem = onNavDestino,
                    id = 5
                )
            }
            item {
                ItemListDesign(
                    text = "OBSERVAÇÃO: ${if(observ.isNullOrEmpty()) "" else observ}",
                    setActionItem = onNavObserv,
                    id = 6
                )
            }
        }
        Button(
            onClick = { onDialogCheck(true) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_button_close_mov))
        }
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Button(
            onClick = onNavMovEquipEditList,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}

        if(status.flagDialog) {
            MsgErrors(status.errors, onCloseDialog, status.failure)
        }

        if(flagDialogCheck){
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_close_mov),
                setCloseDialog = { onDialogCheck(false)  },
                setActionButtonYes = { onCloseMov() }
            )
        }

    }

    LaunchedEffect(flagCloseMov) {
        if (flagCloseMov) {
            onNavMovEquipEditList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DetalheVisitTercPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetalheVisitTercContent(
                dthr = "29/08/2024 16:14",
                tipoMov = "ENTRADA",
                veiculo = "100",
                placa = "ABC-1234",
                tipoVisitTerc = "VISITANTE",
                motorista = "123.456.789-00 - ANDERSON DA SILVA DELGADO",
                passageiro = "123.456.789-00 - JOSE DONIZETE;",
                destino = null,
                observ = "TESTE OBSERVAÇÃO",
                onCloseDialog = {},
                onCloseMov = {},
                flagCloseMov = false,
                flagDialogCheck = false,
                onDialogCheck = {},
                status = UiStatusState(),
                onNavMovEquipEditList = {},
                onNavVeiculo = {},
                onNavPlaca = {},
                onNavCpf = {},
                onNavPassagList = {},
                onNavDestino = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}