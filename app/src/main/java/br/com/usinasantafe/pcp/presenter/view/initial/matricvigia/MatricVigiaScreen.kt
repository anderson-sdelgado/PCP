package br.com.usinasantafe.pcp.presenter.view.initial.matricvigia

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.presenter.theme.TitleDesign
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.presenter.theme.MsgUpdate
import br.com.usinasantafe.pcp.presenter.theme.Progress
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate

const val TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN = "tag_number_text_field_matric_vigia_screen"

@Composable
fun MatricVigiaScreen(
    viewModel: MatricVigiaViewModel = hiltViewModel(),
    onNavMenuInicial: () -> Unit,
    onNavNomeVigia: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MatricVigiaContent(
                text = uiState.text,
                onTextField = viewModel::onTextField,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavMenuInicial = onNavMenuInicial,
                onNavNomeVigia = onNavNomeVigia,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MatricVigiaContent(
    text: String,
    onTextField: (String, TypeButton) -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusStateUpdate,
    onNavMenuInicial: () -> Unit,
    onNavNomeVigia: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = "MATRICULA VIGIA:")
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Previous
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 24.sp
            ),
            readOnly = true,
            value = text,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(setActionButton = onTextField)
        BackHandler {
            onNavMenuInicial()
        }

        if (status.flagDialog) {
            MsgUpdate(status = status, onClickOk = onCloseDialog, value = stringResource(id = R.string.text_title_matric_colab))
        }

        if (status.flagProgress) {
            Progress(status)
        }

    }

    LaunchedEffect(status.flagAccess) {
        if (status.flagAccess) {
            onNavNomeVigia()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricVigiaPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricVigiaContent(
                text = "",
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavMenuInicial = {},
                onNavNomeVigia = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricVigiaPagePreviewFieldEmpty() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricVigiaContent(
                text = "",
                onTextField = { _, _ -> },
                onCloseDialog = {},
                onNavMenuInicial = {},
                onNavNomeVigia = {},
                status = UiStatusStateUpdate(
                    flagAccess = false,
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.FIELD_EMPTY,
                    failure = "",
                    flagProgress = false,
                    currentProgress = 0.0f,
                ),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricVigiaPagePreviewFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricVigiaContent(
                text = "",
                onTextField = { _, _ -> },
                onCloseDialog = {},
                onNavMenuInicial = {},
                onNavNomeVigia = {},
                status = UiStatusStateUpdate(
                    flagAccess = false,
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    failure = "Datasource Failure",
                    flagProgress = false,
                    currentProgress = 0.0f,
                ),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
