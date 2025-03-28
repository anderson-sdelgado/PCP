package br.com.usinasantafe.pcp.presenter.visitterc.moveditlist

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.visitterc.model.MovEquipVisitTercModel
import br.com.usinasantafe.pcp.ui.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcp.ui.theme.TitleDesign

@Composable
fun MovEquipVisitTercEditListScreen(
    viewModel: MovEquipVisitTercEditListViewModel,
    onNavMovEquipList: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MovEquipVisitTercEditListContent(
                movEquipVisitTercModelList = uiState.movEquipVisitTercModelList,
                closeAllMov = viewModel::closeAllMov,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                flagDialogCheck = uiState.flagDialogCheck,
                setDialogCheck = viewModel::setDialogCheck,
                setCloseDialog = viewModel::setCloseDialog,
                flagCloseAllMov = uiState.flagCloseAllMov,
                onNavMovEquipList = onNavMovEquipList,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.recoverMovEquipEditList()
        }
    }
}

@Composable
fun MovEquipVisitTercEditListContent(
    movEquipVisitTercModelList: List<MovEquipVisitTercModel>,
    closeAllMov: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    flagDialogCheck: Boolean,
    setDialogCheck: (Boolean) -> Unit,
    setCloseDialog: () -> Unit,
    flagCloseAllMov: Boolean,
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
            items(movEquipVisitTercModelList) { mov ->
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
            onClick = { setDialogCheck(true) },
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

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
                setActionButtonOK = { onNavMovEquipList() }
            )
        }

        if (flagDialogCheck) {
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_close_all_mov),
                setCloseDialog = { setDialogCheck(false) },
                setActionButtonOK = { closeAllMov() }
            )
        }

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
                movEquipVisitTercModelList = listOf(
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
                closeAllMov = {},
                flagDialog = false,
                failure = "",
                flagDialogCheck = false,
                setDialogCheck = {},
                setCloseDialog = {},
                flagCloseAllMov = false,
                onNavMovEquipList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}