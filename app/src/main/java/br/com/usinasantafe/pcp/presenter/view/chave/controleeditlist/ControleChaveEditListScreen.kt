package br.com.usinasantafe.pcp.presenter.view.chave.controleeditlist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.presenter.theme.MsgUpdate
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.presenter.view.chave.model.ControleChaveModel
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate

@Composable
fun ControleChaveEditListScreen(
    viewModel: ControleChaveEditListViewModel = hiltViewModel(),
    onNavControleList: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recoverMovOpenList()
            }

            ControleChaveEditListContent(
                controleChaveModelList = uiState.controleChaveModelList,
                onCloseAllMov = viewModel::onCloseAllMov,
                flagDialogCheck = uiState.flagDialogCheck,
                onDialogCheck = viewModel::onDialogCheck,
                onCloseDialog = viewModel::onCloseDialog,
                flagCloseAllMov = uiState.flagCloseAllMov,
                status = uiState.status,
                onNavControleList = onNavControleList,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ControleChaveEditListContent(
    controleChaveModelList: List<ControleChaveModel>,
    onCloseAllMov: () -> Unit,
    flagDialogCheck: Boolean,
    onDialogCheck: (Boolean) -> Unit,
    onCloseDialog: () -> Unit,
    flagCloseAllMov: Boolean,
    status: UiStatusState,
    onNavControleList: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(id = R.string.text_title_edit_mov)
        )
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(controleChaveModelList) { cont ->
                ItemListDesign(
                    text = "DATA/HORA: ${cont.dthr}\n" +
                            "TIPO: ${cont.tipoMov}\n" +
                            "CHAVE: ${cont.chave}\n" +
                            "COLABORADOR: ${cont.colab}\n",
                    setActionItem = {
                        onNavDetalhe(cont.id)
                    },
                    id = cont.id,
                    padding = 0
                )
            }
        }
        Button(
            onClick = { onDialogCheck(true) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_close_mov
                )
            )
        }
        Button(
            onClick = onNavControleList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_pattern_return
                )
            )
        }
        BackHandler {}

        if(status.flagDialog) {
            MsgErrors(status.errors, onCloseDialog, status.failure)
        }

        if(flagDialogCheck){
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_close_all_mov),
                setCloseDialog = { onDialogCheck(false)  },
                setActionButtonYes = { onCloseAllMov() }
            )
        }

    }

    LaunchedEffect(flagCloseAllMov) {
        if(flagCloseAllMov){
            onNavControleList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ControleChaveEditListPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ControleChaveEditListContent(
                controleChaveModelList = emptyList(),
                onCloseAllMov = {},
                flagDialogCheck = false,
                onDialogCheck = {},
                onCloseDialog = {},
                flagCloseAllMov = false,
                status = UiStatusState(),
                onNavControleList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ControleChaveEditListPagePreviewWithData() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ControleChaveEditListContent(
                controleChaveModelList = listOf(
                    ControleChaveModel(
                        id = 1,
                        tipoMov = "RETIRADA",
                        dthr = "20/10/2024",
                        chave = "01 - SALA TI - TI",
                        colab = "19795 - ANDERSON DA SILVA DELGADO"
                    ),
                    ControleChaveModel(
                        id = 2,
                        tipoMov = "DEVOLUÇÃO",
                        dthr = "20/10/2024",
                        chave = "01 - SALA TI - TI",
                        colab = "19795 - ANDERSON DA SILVA DELGADO"
                    )
                ),
                onCloseAllMov = {},
                flagDialogCheck = false,
                onDialogCheck = {},
                onCloseDialog = {},
                flagCloseAllMov = false,
                status = UiStatusState(),
                onNavControleList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}