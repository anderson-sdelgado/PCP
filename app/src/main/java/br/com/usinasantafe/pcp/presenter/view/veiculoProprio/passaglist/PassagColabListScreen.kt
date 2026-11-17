package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.passaglist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.utils.UiStatusState

@Composable
fun PassagColabListScreen(
    viewModel: PassagColabListViewModel = hiltViewModel(),
    onNavMatricMotorista: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavMatricPassag: () -> Unit,
    onNavDestino: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover(true)
            }

            PassagColabListContent(
                flowApp = uiState.flowApp,
                list = uiState.list,
                setDelete = viewModel::setDelete,
                flagDialogCheck = uiState.flagDialogCheck,
                onCloseDialogCheck = viewModel::onCloseDialogCheck,
                onDelete = viewModel::onDelete,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavMatricColab = onNavMatricMotorista,
                onNavDetalhe = onNavDetalhe,
                onNavMatricPassag = onNavMatricPassag,
                onNavDestino = onNavDestino,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun PassagColabListContent(
    flowApp: FlowApp,
    list: List<Colab>,
    setDelete: (Int) -> Unit,
    flagDialogCheck: Boolean,
    onCloseDialogCheck: () -> Unit,
    onDelete: () -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusState,
    onNavMatricColab: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavMatricPassag: () -> Unit,
    onNavDestino: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_passag))
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(list) { colab ->
                ItemListDesign(
                    text = "${colab.matricColab} - ${colab.nomeColab}",
                    setActionItem = { setDelete(colab.matricColab) },
                    id = colab.matricColab
                )
            }
        }
        Button(
            onClick = { onNavMatricPassag() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_insert))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    when (flowApp) {
                        FlowApp.ADD -> onNavMatricColab()
                        FlowApp.CHANGE -> onNavDetalhe()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = {
                    when (flowApp) {
                        FlowApp.ADD -> onNavDestino()
                        FlowApp.CHANGE -> onNavDetalhe()
                    }
                },
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}

        if(status.flagDialog) {
            MsgErrors(status.errors, onCloseDialog, status.failure)
        }

        if (flagDialogCheck) {
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_delete_passag),
                setCloseDialog = onCloseDialogCheck,
                setActionButtonYes = onDelete
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PassagColabListPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PassagColabListContent(
                list = emptyList(),
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = false,
                onCloseDialogCheck = {},
                onDelete = {},
                onCloseDialog = {},
                status = UiStatusState(),
                onNavMatricColab = {},
                onNavDetalhe = {},
                onNavMatricPassag = {},
                onNavDestino = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PassagColabListPagePreviewList() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PassagColabListContent(
                list = listOf(
                    Colab(
                        matricColab = 19759,
                        nomeColab = "ANDERSON DA SILVA DELGADO"
                    ),
                    Colab(
                        matricColab = 19035,
                        nomeColab = "JOSE DONIZETE"
                    )
                ),
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = false,
                onCloseDialogCheck = {},
                onDelete = {},
                onCloseDialog = {},
                status = UiStatusState(),
                onNavMatricColab = {},
                onNavDetalhe = {},
                onNavMatricPassag = {},
                onNavDestino = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PassagColabListPagePreviewFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PassagColabListContent(
                list = listOf(
                    Colab(
                        matricColab = 19759,
                        nomeColab = "ANDERSON DA SILVA DELGADO"
                    ),
                    Colab(
                        matricColab = 19035,
                        nomeColab = "JOSE DONIZETE"
                    )
                ),
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = false,
                onCloseDialogCheck = {},
                onDelete = {},
                onCloseDialog = {},
                status = UiStatusState(),
                onNavMatricColab = {},
                onNavDetalhe = {},
                onNavMatricPassag = {},
                onNavDestino = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PassagColabListPagePreviewMsgDelete() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PassagColabListContent(
                list = listOf(
                    Colab(
                        matricColab = 19759,
                        nomeColab = "ANDERSON DA SILVA DELGADO"
                    ),
                    Colab(
                        matricColab = 19035,
                        nomeColab = "JOSE DONIZETE"
                    )
                ),
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = true,
                onCloseDialogCheck = {},
                onDelete = {},
                onCloseDialog = {},
                status = UiStatusState(),
                onNavMatricColab = {},
                onNavDetalhe = {},
                onNavMatricPassag = {},
                onNavDestino = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}