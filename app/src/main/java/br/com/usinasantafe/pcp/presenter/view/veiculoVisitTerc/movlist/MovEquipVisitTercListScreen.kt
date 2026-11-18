package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.movlist

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
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TextSmallDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.presenter.model.MovEquipVisitTercModel
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.utils.UiStatusState

@Composable
fun MovEquipVisitTercListScreen(
    viewModel: MovEquipVisitTercListViewModel = hiltViewModel(),
    onNavVeiculo: () -> Unit,
    onNavMovEquipEditList: () -> Unit,
    onNavObserv: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
                viewModel.list()
            }

            MovEquipVisitTercListContent(
                list = uiState.list,
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                onStartMov = viewModel::onStartMov,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavVeiculo = onNavVeiculo,
                onNavMovEquipEditList = onNavMovEquipEditList,
                onNavObserv = onNavObserv,
                onNavMenuApont = onNavMenuApont,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MovEquipVisitTercListContent(
    list: List<MovEquipVisitTercModel>,
    descrVigia: String,
    descrLocal: String,
    onStartMov: () -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusState,
    onNavVeiculo: () -> Unit,
    onNavMovEquipEditList: () -> Unit,
    onNavObserv: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TextSmallDesign(text = "VIGIA: $descrVigia")
        TextSmallDesign(text = "LOCAL: $descrLocal")
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        TitleDesign(
            text = stringResource(id = R.string.text_title_mov_visit_terc)
        )
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(list) { mov ->
                ItemListDesign(
                    text = "DATA/HORA: ${mov.dthr}\n" +
                            "VEÍCULO: ${mov.veiculo}\n" +
                            "PLACA: ${mov.placa}\n" +
                            "${mov.tipoVisitTerc}\n" +
                            "MOTORISTA: ${mov.motorista}\n",
                    setActionItem = {
                        onNavObserv(mov.id)
                    },
                    id = mov.id,
                    padding = 0
                )
            }
        }
        Button(
            onClick = onStartMov,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_input))
        }
        Button(
            onClick = onNavMovEquipEditList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_edit_mov))
        }
        Button(
            onClick = onNavMenuApont,
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

    }

    LaunchedEffect(status.flagAccess) {
        if (status.flagAccess) {
            onNavVeiculo()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovEquipVisitTercListPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipVisitTercListContent(
                onStartMov = {},
                descrVigia = "19759 - ANDERSON DA SILVA DELGADO",
                descrLocal = "1 - USINA",
                list = listOf(
                    MovEquipVisitTercModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        motorista = "326.949.728-88 - ANDERSON DA SILVA DELGADO",
                        veiculo = "GOL",
                        placa = "ABC1234",
                        tipoVisitTerc = "TERCEIRO"
                    ),
                    MovEquipVisitTercModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        motorista = "123.456.789-00 - TESTE",
                        veiculo = "GOL",
                        placa = "ABC1234",
                        tipoVisitTerc = "TERCEIRO"
                    )
                ),
                onCloseDialog = {},
                status = UiStatusState(),
                onNavVeiculo = {},
                onNavMovEquipEditList = {},
                onNavObserv = {},
                onNavMenuApont = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}