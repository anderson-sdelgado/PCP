package br.com.usinasantafe.pcp.presenter.view.configuration.menuinicial

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.BuildConfig
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.presenter.theme.ItemListDesign
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.presenter.theme.colorStatus
import br.com.usinasantafe.pcp.presenter.theme.textStatus
import br.com.usinasantafe.pcp.utils.UiStatusState

@Composable
fun MenuInicialScreen(
    viewModel: MenuInicialViewModel = hiltViewModel(),
    onNavMatricVigia: () -> Unit,
    onNavSenha: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MenuInicialContent(
                statusSend = uiState.statusSend,
                onCloseDialog = viewModel::onCloseDialog,
                onCheckAccess = viewModel::onCheckAccess,
                status = uiState.status,
                onNavMatricVigia = onNavMatricVigia,
                onNavSenha = onNavSenha,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun MenuInicialContent(
    statusSend: StatusSend,
    onCloseDialog: () -> Unit,
    onCheckAccess: () -> Unit,
    status: UiStatusState,
    onNavMatricVigia: () -> Unit,
    onNavSenha: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as? Activity)
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = "MENU INICIAL - V ${BuildConfig.VERSION_NAME}",
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ItemListDesign(
                    text = "APONTAMENTO",
                    setActionItem = onCheckAccess,
                    font = 26
                )
            }
            item {
                ItemListDesign(
                    text = "CONFIGURAÇÃO",
                    setActionItem = onNavSenha,
                    font = 26
                )
            }
            item {
                ItemListDesign(
                    text = "SAIR",
                    setActionItem = {
                        activity?.finish()
                    },
                    font = 26
                )
            }
        }
        Text(
            textAlign = TextAlign.Left,
            text = textStatus(status.failure, statusSend),
            fontSize = 22.sp,
            color = colorStatus(status.failure, statusSend),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        BackHandler {}

        if(status.flagDialog) {
            MsgErrors(status.errors, onCloseDialog, status.failure)
        }

    }

    LaunchedEffect(status.flagAccess) {
        if (status.flagAccess) {
            onNavMatricVigia()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicialPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuInicialContent(
                statusSend = StatusSend.STARTED,
                onCloseDialog = {},
                status = UiStatusState(
                    flagDialog = false,
                    flagFailure = false,
                    flagAccess = false,
                    failure = "",
                ),
                onCheckAccess = {},
                onNavMatricVigia = {},
                onNavSenha = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicialPagePreviewOpenDialog() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuInicialContent(
                statusSend = StatusSend.SEND,
                onCloseDialog = {},
                status = UiStatusState(
                    flagDialog = false,
                    flagFailure = false,
                    flagAccess = false,
                    failure = "",
                ),
                onCheckAccess = {},
                onNavMatricVigia = {},
                onNavSenha = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicialPagePreviewOpenDialogFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuInicialContent(
                statusSend = StatusSend.SENT,
                onCloseDialog = {},
                status = UiStatusState(
                    flagDialog = true,
                    flagFailure = true,
                    flagAccess = false,
                    failure = "Failure",
                ),
                onCheckAccess = {},
                onNavMatricVigia = {},
                onNavSenha = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicialPagePreviewError() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuInicialContent(
                statusSend = StatusSend.STARTED,
                onCloseDialog = {},
                status = UiStatusState(
                    flagDialog = true,
                    flagFailure = false,
                    flagAccess = false,
                    failure = "",
                ),
                onCheckAccess = {},
                onNavMatricVigia = {},
                onNavSenha = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}