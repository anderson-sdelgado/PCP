package br.com.usinasantafe.pcp.presenter.view.chave.detalhe

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.utils.UiStatusState

@Composable
fun DetalheChaveScreen(
    viewModel: DetalheChaveViewModel = hiltViewModel(),
    onNavControleChaveEditList: () -> Unit,
    onNavMatricColab: () -> Unit,
    onNavObserv: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recoverDetalhe()
            }

            DetalheChaveContent(
                dthr = uiState.dthr,
                tipoMov = uiState.tipoMov,
                chave = uiState.chave,
                colab = uiState.colab,
                observ = uiState.observ,
                flagDialogCheck = uiState.flagDialogCheck,
                flagCloseMov = uiState.flagCloseMov,
                onCloseDialog = viewModel::onCloseDialog,
                onCloseMov = viewModel::onCloseMov,
                status = uiState.status,
                onDialogCheck = viewModel::onDialogCheck,
                onNavControleChaveEditList = onNavControleChaveEditList,
                onNavMatricColab = onNavMatricColab,
                onNavObserv = onNavObserv,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DetalheChaveContent(
    dthr: String,
    tipoMov: String,
    chave: String,
    colab: String,
    observ: String?,
    flagDialogCheck: Boolean,
    flagCloseMov: Boolean,
    onCloseDialog: () -> Unit,
    onCloseMov: () -> Unit,
    onDialogCheck: (Boolean) -> Unit,
    status: UiStatusState,
    onNavControleChaveEditList: () -> Unit,
    onNavMatricColab: () -> Unit,
    onNavObserv: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_detalhe
            )
        )
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
                    text = "CHAVE: $chave",
                    setActionItem = {}
                )
            }
            item {
                ItemListDesign(
                    text = "COLABORADOR: $colab",
                    setActionItem = onNavMatricColab,
                    id = 1
                )
            }
            item {
                ItemListDesign(
                    text = "OBSERVAÇÃO: ${if(observ.isNullOrEmpty()) "" else observ}",
                    setActionItem = onNavObserv,
                    id = 2
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
            onClick = onNavControleChaveEditList,
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
        if(flagCloseMov){
            onNavControleChaveEditList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalheChavePagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetalheChaveContent(
                dthr = "17/10/2024 10:30",
                tipoMov = "RETIRADA",
                chave = "01 - SALA TI - TI",
                colab = "19759 - ANDERSON DA SILVA DELGADO",
                observ = "TESTE",
                flagDialogCheck = false,
                flagCloseMov = false,
                onCloseDialog = {},
                onCloseMov = {},
                onDialogCheck = {},
                status = UiStatusState(),
                onNavControleChaveEditList = {},
                onNavMatricColab = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}