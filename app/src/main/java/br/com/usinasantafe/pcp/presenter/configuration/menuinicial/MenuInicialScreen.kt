package br.com.usinasantafe.pcp.presenter.configuration.menuinicial

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.BuildConfig
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TitleDesign
import br.com.usinasantafe.pcp.utils.StatusSend

@Composable
fun MenuInicialScreen(
    viewModel: MenuInicialViewModel,
    onNavMatricVigia: () -> Unit,
    onNavSenha: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MenuInicialContent(
                statusSend = uiState.statusSend,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                failure = uiState.failure,
                failureStatus = uiState.failureStatus,
                flagAccess = uiState.flagAccess,
                onCheckAccess = viewModel::checkAccess,
                onNavMatricVigia = onNavMatricVigia,
                onNavSenha = onNavSenha,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.recoverStatusSend()
        }
    }
}

@Composable
fun MenuInicialContent(
    statusSend: StatusSend,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    failure: String,
    failureStatus: String,
    flagAccess: Boolean,
    onCheckAccess: () -> Unit,
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
            text =
                if(failureStatus.isEmpty()) {
                    when (statusSend) {
                        StatusSend.STARTED -> stringResource(id = R.string.text_status_started)
                        StatusSend.SEND -> stringResource(id = R.string.text_status_send)
                        StatusSend.SENDING -> stringResource(id = R.string.text_status_sending)
                        StatusSend.SENT -> stringResource(id = R.string.text_status_sent)
                    }
                } else {
                    "Failure: $failureStatus"
                }
            ,
            fontSize = 22.sp,
            color =
                if(failureStatus.isEmpty()) {
                    when (statusSend) {
                        StatusSend.STARTED -> Color.Red
                        StatusSend.SEND -> Color.Red
                        StatusSend.SENDING -> Color.Yellow
                        StatusSend.SENT -> Color.Green
                    }
                } else {
                    Color.Red
                }
            ,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        BackHandler {}

        if (flagDialog) {
            val text =
                if (!flagFailure) {
                    stringResource(id = R.string.text_blocked_access_app)
                } else {
                    stringResource(
                        id = R.string.text_failure,
                        failure
                    )
                }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagAccess) {
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
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                flagAccess = false,
                failure = "",
                failureStatus = "",
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
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = false,
                flagAccess = false,
                failure = "",
                failureStatus = "",
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
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                flagAccess = false,
                failure = "Failure Datasource",
                onCheckAccess = {},
                failureStatus = "",
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
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                flagAccess = false,
                failure = "",
                failureStatus = "Error",
                onCheckAccess = {},
                onNavMatricVigia = {},
                onNavSenha = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}