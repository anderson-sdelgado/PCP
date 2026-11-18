package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.tipo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TextButtonDesign
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.utils.UiStatusState

@Composable
fun TipoVisitTercScreen(
    viewModel: TipoVisitTercViewModel = hiltViewModel(),
    onNavPlacaVisitTerc: () -> Unit,
    onNavCpfVisitTerc: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            TipoVisitTercContent(
                set = viewModel::set,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavPlacaVisitTerc = onNavPlacaVisitTerc,
                onNavCpfVisitTerc = onNavCpfVisitTerc,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun TipoVisitTercContent(
    set: (TypeVisitTerc) -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusState,
    onNavPlacaVisitTerc: () -> Unit,
    onNavCpfVisitTerc: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_tipo))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ItemListDesign(
                    text = "TERCEIRO",
                    setActionItem = { set(TypeVisitTerc.TERCEIRO) },
                    font = 26
                )
            }
            item {
                ItemListDesign(
                    text = "VISITANTE",
                    setActionItem = { set(TypeVisitTerc.VISITANTE) },
                    font = 26
                )
            }
        }
        Button(
            onClick = onNavPlacaVisitTerc,
            modifier = Modifier.fillMaxWidth(),
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
            onNavCpfVisitTerc()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TipoVisitTercPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TipoVisitTercContent(
                set = {},
                onCloseDialog = {},
                status = UiStatusState(),
                onNavPlacaVisitTerc = {},
                onNavCpfVisitTerc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}