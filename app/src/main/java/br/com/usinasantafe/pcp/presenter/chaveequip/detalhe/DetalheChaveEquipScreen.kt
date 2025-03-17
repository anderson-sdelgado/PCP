package br.com.usinasantafe.pcp.presenter.chaveequip.detalhe

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.ui.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcp.ui.theme.TitleDesign

@Composable
fun DetalheChaveEquipScreen(
    viewModel: DetalheChaveEquipViewModel,
    onNavControleChaveEquipEditList: () -> Unit,
    onNavMatricColab: () -> Unit,
    onNavObserv: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DetalheChaveEquipContent(
                dthr = uiState.dthr,
                tipoMov = uiState.tipoMov,
                equip = uiState.equip,
                colab = uiState.colab,
                observ = uiState.observ,
                flagDialog = uiState.flagDialog,
                flagDialogCheck = uiState.flagDialogCheck,
                flagCloseMov = uiState.flagCloseMov,
                failure = uiState.failure,
                setCloseDialog = viewModel::setCloseDialog,
                closeMov = viewModel::closeMov,
                setDialogCheck = viewModel::setDialogCheck,
                onNavControleChaveEquipEditList = onNavControleChaveEquipEditList,
                onNavMatricColab = onNavMatricColab,
                onNavObserv = onNavObserv,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.recoverDetalhe()
        }
    }
}

@Composable
fun DetalheChaveEquipContent(
    dthr: String,
    tipoMov: String,
    equip: String,
    colab: String,
    observ: String?,
    flagDialog: Boolean,
    flagDialogCheck: Boolean,
    flagCloseMov: Boolean,
    failure: String,
    setCloseDialog: () -> Unit,
    closeMov: () -> Unit,
    setDialogCheck: (Boolean) -> Unit,
    onNavControleChaveEquipEditList: () -> Unit,
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
                    text = "EQUIPAMENTO: $equip",
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
            onClick = { setDialogCheck(true) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_button_close_mov))
        }
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Button(
            onClick = onNavControleChaveEquipEditList,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}

        if(flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
                setActionButtonOK = { onNavControleChaveEquipEditList() }
            )
        }

        if(flagDialogCheck){
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_close_mov),
                setCloseDialog = { setDialogCheck(false)  },
                setActionButtonOK = { closeMov() }
            )
        }

        if(flagCloseMov){
            onNavControleChaveEquipEditList()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetalheChaveEquipPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetalheChaveEquipContent(
                dthr = "17/10/2024 10:30",
                tipoMov = "RETIRADA",
                equip = "300 - EQUIPAMENTO TESTE",
                colab = "19759 - ANDERSON DA SILVA DELGADO",
                observ = "TESTE",
                flagDialog = false,
                flagDialogCheck = false,
                flagCloseMov = false,
                failure = "",
                setCloseDialog = {},
                closeMov = {},
                setDialogCheck = {},
                onNavControleChaveEquipEditList = {},
                onNavMatricColab = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}