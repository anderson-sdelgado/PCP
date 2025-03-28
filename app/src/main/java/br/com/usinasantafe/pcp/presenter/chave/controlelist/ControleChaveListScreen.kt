package br.com.usinasantafe.pcp.presenter.chave.controlelist

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
import br.com.usinasantafe.pcp.presenter.chave.model.ControleChaveModel
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcp.ui.theme.TextSmallDesign
import br.com.usinasantafe.pcp.ui.theme.TitleDesign

@Composable
fun ControleChaveListScreen(
    viewModel: ControleChaveListViewModel,
    onNavControleChaveEditList: () -> Unit,
    onNavMatricColab: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
    onNavChaveList: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ControleChaveListContent(
                controleChaveModelList = uiState.controleChaveModelList,
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                startMov = viewModel::startMov,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavControleChaveEditList = onNavControleChaveEditList,
                onNavMatricColab = onNavMatricColab,
                onNavMenuApont = onNavMenuApont,
                onNavChaveList = onNavChaveList,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.returnHeader()
            viewModel.recoverMovList()
        }
    }
}

@Composable
fun ControleChaveListContent(
    controleChaveModelList: List<ControleChaveModel>,
    descrVigia: String,
    descrLocal: String,
    startMov: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavControleChaveEditList: () -> Unit,
    onNavMatricColab: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
    onNavChaveList: () -> Unit,
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
            text = stringResource(id = R.string.text_title_controle_chave)
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
                            "CHAVE: ${cont.chave}\n" +
                            "COLABORADOR: ${cont.colab}\n",
                    setActionItem = {
                        onNavMatricColab(cont.id)
                    },
                    id = cont.id,
                    padding = 0
                )
            }
        }
        Button(
            onClick = startMov,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_remove_key)
            )
        }
        Button(
            onClick = onNavControleChaveEditList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_edit_mov)
            )
        }
        Button(
            onClick = onNavMenuApont,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_return)
            )
        }
        BackHandler {}

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagAccess) {
            onNavChaveList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ControleChaveListPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ControleChaveListContent(
                controleChaveModelList = listOf(
                    ControleChaveModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        chave = "01 - SALA TI - TI",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    )
                ),
                descrVigia = "19759 - ANDERSON DA SILVA DELGADO",
                descrLocal = "1 - USINA",
                startMov = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavControleChaveEditList = {},
                onNavMatricColab = {},
                onNavMenuApont = {},
                onNavChaveList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}