package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.moveditlist

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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.presenter.model.MovEquipVisitTercModel
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.utils.UiStatusState

@Composable
fun MovEquipVisitTercEditListScreen(
    viewModel: MovEquipVisitTercEditListViewModel = hiltViewModel(),
    onNavMovEquipList: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            MovEquipVisitTercEditListContent(
                list = uiState.list,
                onCloseAllMov = viewModel::onCloseAllMov,
                flagDialogCheck = uiState.flagDialogCheck,
                onDialogCheck = viewModel::onDialogCheck,
                onCloseDialog = viewModel::onCloseDialog,
                flagCloseAllMov = uiState.flagCloseAllMov,
                status = uiState.status,
                onNavMovEquipList = onNavMovEquipList,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MovEquipVisitTercEditListContent(
    list: List<MovEquipVisitTercModel>,
    onCloseAllMov: () -> Unit,
    flagDialogCheck: Boolean,
    onDialogCheck: (Boolean) -> Unit,
    onCloseDialog: () -> Unit,
    flagCloseAllMov: Boolean,
    status: UiStatusState,
    onNavMovEquipList: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        TitleDesign(
            text = stringResource(id = R.string.text_title_edit_mov)
        )
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(list) { mov ->
                ItemListDesign(
                    text = "${mov.dthr}\n" +
                            "${mov.tipoMov}\n" +
                            "${mov.veiculo}\n" +
                            "${mov.placa}\n" +
                            "${mov.motorista}\n",
                    setActionItem = {
                        onNavDetalhe(mov.id)
                    },
                    id = mov.id,
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
            TextButtonDesign(text = stringResource(id = R.string.text_close_mov))
        }
        Button(
            onClick = onNavMovEquipList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}

        if(status.flagDialog) {
            MsgErrors(status.errors, onCloseDialog, status.failure)
        }

        if (flagDialogCheck) {
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_close_all_mov),
                setCloseDialog = { onDialogCheck(false) },
                setActionButtonYes = { onCloseAllMov() }
            )
        }

    }

    LaunchedEffect(flagCloseAllMov) {
        if (flagCloseAllMov) {
            onNavMovEquipList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MovEquipVisitTercEditListPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipVisitTercEditListContent(
                list = listOf(
                    MovEquipVisitTercModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        motorista = "326.949.728-88 - ANDERSON DA SILVA DELGADO",
                        veiculo = "GOL",
                        placa = "ABC1234",
                        tipoVisitTerc = "TERCEIRO",
                        tipoMov = "SAÍDA"
                    ),
                    MovEquipVisitTercModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        motorista = "123.456.789-00 - TESTE",
                        veiculo = "GOL",
                        placa = "ABC1234",
                        tipoVisitTerc = "TERCEIRO",
                        tipoMov = "ENTRADA"
                    )
                ),
                onCloseAllMov = {},
                flagDialogCheck = false,
                onDialogCheck = {},
                onCloseDialog = {},
                flagCloseAllMov = false,
                status = UiStatusState(),
                onNavMovEquipList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}