package br.com.usinasantafe.pcp.presenter.view.initial.local

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
import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.presenter.theme.MsgUpdate
import br.com.usinasantafe.pcp.presenter.theme.Progress
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate

@Composable
fun LocalScreen(
    viewModel: LocalViewModel = hiltViewModel(),
    onNavNomeVigia: () -> Unit,
    onNavMenuApont: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.localList()
            }

            LocalContent(
                locals = uiState.locals,
                setIdLocal = viewModel::set,
                updateDatabase = viewModel::updateDatabase,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavNomeVigia = onNavNomeVigia,
                onNavMenuApont = onNavMenuApont,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun LocalContent(
    locals: List<Local>,
    setIdLocal: (Int) -> Unit,
    updateDatabase: () -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusStateUpdate,
    onNavNomeVigia: () -> Unit,
    onNavMenuApont: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = "LOCAL")
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(locals) { local ->
                ItemListDesign(
                    text = local.descrLocal,
                    setActionItem = { setIdLocal(local.idLocal) },
                    font = 26
                )
            }
        }
        Button(
            onClick = updateDatabase,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_update))
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Button(
            onClick = onNavNomeVigia,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}

        if (status.flagDialog) {
            MsgUpdate(status = status, onClickOk = onCloseDialog, value = stringResource(id = R.string.text_title_matric_colab))
        }

        if (status.flagProgress) {
            Progress(status)
        }

    }

    LaunchedEffect(status.flagAccess) {
        if (status.flagAccess) {
            onNavMenuApont()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LocalContent(
                locals = listOf(
                    Local(
                        idLocal = 1,
                        "USINA"
                    ),
                    Local(
                        idLocal = 2,
                        "MOTO"
                    ),
                    Local(
                        idLocal = 3,
                        "RESIDÊNCIA"
                    ),
                ),
                setIdLocal = {},
                updateDatabase = {},
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavNomeVigia = {},
                onNavMenuApont = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewShowAlertDialog() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LocalContent(
                locals = listOf(
                    Local(
                        idLocal = 1,
                        "USINA"
                    ),
                    Local(
                        idLocal = 2,
                        "MOTO"
                    ),
                    Local(
                        idLocal = 3,
                        "RESIDÊNCIA"
                    ),
                ),
                setIdLocal = {},
                updateDatabase = {},
                onCloseDialog = {},
                status = UiStatusStateUpdate(
                    flagAccess = false,
                    flagDialog = true,
                    flagFailure = false,
                    errors = Errors.FIELD_EMPTY,
                    failure = "",
                    flagProgress = false,
                    currentProgress = 0.0f,
                ),
                onNavNomeVigia = {},
                onNavMenuApont = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}