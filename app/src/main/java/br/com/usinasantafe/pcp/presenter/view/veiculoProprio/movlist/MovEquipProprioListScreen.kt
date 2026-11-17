package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.movlist

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
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TextSmallDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.presenter.model.MovEquipProprioModel
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.utils.UiStatusState

@Composable
fun MovEquipProprioListScreen(
    viewModel: MovEquipProprioListViewModel = hiltViewModel(),
    onNavNroEquip: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
    onNavSplashScreen: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
                viewModel.list()
            }

            MovEquipProprioListContent(
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                list = uiState.list,
                onStartMov = viewModel::onStartMov,
                flagDialogCheck = uiState.flagDialogCheck,
                onDialogCheck = viewModel::onDialogCheck,
                onCloseAllMov = viewModel::onCloseAllMov,
                flagCloseAllMov = uiState.flagCloseAllMov,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavNroEquip = onNavNroEquip,
                onNavDetalhe = onNavDetalhe,
                onNavMenuApont = onNavMenuApont,
                onNavSplashScreen = onNavSplashScreen,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MovEquipProprioListContent(
    descrVigia: String,
    descrLocal: String,
    list: List<MovEquipProprioModel>,
    onStartMov: (TypeMovEquip) -> Unit,
    flagDialogCheck: Boolean,
    onDialogCheck: (Boolean) -> Unit,
    onCloseAllMov: () -> Unit,
    flagCloseAllMov: Boolean,
    onCloseDialog: () -> Unit,
    status: UiStatusState,
    onNavNroEquip: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
    onNavSplashScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TextSmallDesign(text = "VIGIA: $descrVigia")
        TextSmallDesign(text = "LOCAL: $descrLocal")
        TitleDesign(text = stringResource(id = R.string.text_title_mov_proprio))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(list) { mov ->
                ItemListDesign(
                    text = "DATA/HORA: ${mov.dthr}\n" +
                            "${mov.typeMov}\n" +
                            "VEÍCULO: ${mov.equip}\n" +
                            "MOTORISTA: ${mov.colab}\n",
                    setActionItem = {
                        onNavDetalhe(mov.id)
                    },
                    id = mov.id
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { onStartMov(TypeMovEquip.INPUT) },
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_input))
            }
            Button(
                onClick = { onStartMov(TypeMovEquip.OUTPUT) },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_output))
            }
        }
        Button(
            onClick = { onDialogCheck(true) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_close_mov))
        }
        Button(
            onClick = onNavMenuApont,
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
                text = stringResource(id = R.string.text_question_close_all_mov),
                setCloseDialog = { onDialogCheck(false)  },
                setActionButtonYes = { onCloseAllMov() }
            )
        }

    }

    LaunchedEffect(status.flagAccess) {
        if (status.flagAccess) {
            onNavNroEquip()
        }
    }

    LaunchedEffect(flagCloseAllMov) {
        if (flagCloseAllMov) {
            onNavSplashScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovEquipProprioListPreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipProprioListContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                list = listOf(
                    MovEquipProprioModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        typeMov = "ENTRADA",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    ),
                    MovEquipProprioModel(
                        id = 2,
                        dthr = "08/08/2024 12:00",
                        typeMov = "ENTRADA",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    )
                ),
                onStartMov = {},
                flagDialogCheck = false,
                onDialogCheck = {},
                onCloseAllMov = {},
                flagCloseAllMov = false,
                onCloseDialog = {},
                status = UiStatusState(),
                onNavNroEquip = {},
                onNavDetalhe = {},
                onNavMenuApont = {},
                onNavSplashScreen = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovEquipProprioListPreviewShowDialog() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipProprioListContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                list = listOf(
                    MovEquipProprioModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        typeMov = "Entrada",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    ),
                    MovEquipProprioModel(
                        id = 2,
                        dthr = "08/08/2024 12:00",
                        typeMov = "ENTRADA",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    )
                ),
                onStartMov = {},
                flagDialogCheck = false,
                onDialogCheck = {},
                onCloseAllMov = {},
                flagCloseAllMov = false,
                onCloseDialog = {},
                status = UiStatusState(),
                onNavNroEquip = {},
                onNavDetalhe = {},
                onNavMenuApont = {},
                onNavSplashScreen = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}