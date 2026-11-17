package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.detalhe

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
fun DetalheMovProprioScreen(
    viewModel: DetalheProprioViewModel = hiltViewModel(),
    onNavMovProprioList: () -> Unit,
    onNavNroEquip: () -> Unit,
    onNavEquipSegList: () -> Unit,
    onNavMatricColab: () -> Unit,
    onNavPassagList: () -> Unit,
    onNavDestino: () -> Unit,
    onNavNotaFiscal: () -> Unit,
    onNavObserv: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            DetalheMovProprioContent(
                dthr = uiState.dthr,
                tipoMov = uiState.tipoMov,
                veiculo = uiState.veiculo,
                motorista = uiState.motorista,
                passageiro = uiState.passageiro,
                destino = uiState.destino,
                veiculoSec = uiState.veiculoSec,
                notaFiscal = uiState.notaFiscal,
                observ = uiState.observ,
                onCloseDialog = viewModel::onCloseDialog,
                onCloseMov = viewModel::onCloseMov,
                flagCloseMov = uiState.flagCloseMov,
                flagDialogCheck = uiState.flagDialogCheck,
                onDialogCheck = viewModel::onDialogCheck,
                status = uiState.status,
                onNavMovProprioList = onNavMovProprioList,
                onNavNroEquip = onNavNroEquip,
                onNavEquipSegList = onNavEquipSegList,
                onNavMatricColab = onNavMatricColab,
                onNavPassagList = onNavPassagList,
                onNavDestino = onNavDestino,
                onNavNotaFiscal = onNavNotaFiscal,
                onNavObserv = onNavObserv,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DetalheMovProprioContent(
    dthr: String,
    tipoMov: String,
    veiculo: String,
    motorista: String,
    passageiro: String,
    destino: String,
    veiculoSec: String,
    notaFiscal: String?,
    observ: String?,
    onCloseDialog: () -> Unit,
    onCloseMov: () -> Unit,
    flagCloseMov: Boolean,
    flagDialogCheck: Boolean,
    onDialogCheck: (Boolean) -> Unit,
    status: UiStatusState,
    onNavMovProprioList: () -> Unit,
    onNavNroEquip: () -> Unit,
    onNavEquipSegList: () -> Unit,
    onNavMatricColab: () -> Unit,
    onNavPassagList: () -> Unit,
    onNavDestino: () -> Unit,
    onNavNotaFiscal: () -> Unit,
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
                    setActionItem = onNavNroEquip,
                    id = 1
                )
            }
            item {
                ItemListDesign(
                    text = "VEICULO SECUNDÁRIO: $veiculoSec",
                    setActionItem = onNavEquipSegList,
                    id = 2
                )
            }
            item {
                ItemListDesign(
                    text = "MOTORISTA: $motorista",
                    setActionItem = onNavMatricColab,
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
                    text = "DESTINO: $destino",
                    setActionItem = onNavDestino,
                    id = 5
                )
            }
            item {
                ItemListDesign(
                    text = "NOTA FISCAL: ${if(notaFiscal.isNullOrEmpty()) "" else notaFiscal}",
                    setActionItem = onNavNotaFiscal,
                    id = 6
                )
            }
            item {
                ItemListDesign(
                    text = "OBSERVAÇÃO: ${if(observ.isNullOrEmpty()) "" else observ}",
                    setActionItem = onNavObserv,
                    id = 7
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
            onClick = onNavMovProprioList,
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
                text = stringResource(id = R.string.text_question_return),
                setCloseDialog = { onDialogCheck(false)  },
                setActionButtonYes = { onCloseMov() }
            )
        }

    }

    LaunchedEffect(flagCloseMov) {
        if (flagCloseMov) {
            onNavMovProprioList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalheMovProprioPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetalheMovProprioContent(
                dthr = "29/08/2024 16:14",
                tipoMov = "ENTRADA",
                veiculo = "100",
                veiculoSec = "101 - 200",
                motorista = "19759 - ANDERSON DA SILVA DELGADO",
                passageiro = "19035 - JOSE DONIZETE; 18017 - RONALDO;",
                destino = "TESTE DESTINO",
                notaFiscal = "NOTA FISCAL:",
                observ = "TESTE OBSERVAÇÃO",
                status = UiStatusState(),
                onCloseDialog = {},
                flagDialogCheck = false,
                onDialogCheck = {},
                onCloseMov = {},
                flagCloseMov = false,
                onNavMovProprioList = {},
                onNavNroEquip = {},
                onNavEquipSegList = {},
                onNavMatricColab = {},
                onNavPassagList = {},
                onNavDestino = {},
                onNavNotaFiscal = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}