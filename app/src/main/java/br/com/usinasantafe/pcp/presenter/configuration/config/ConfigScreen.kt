package br.com.usinasantafe.pcp.presenter.configuration.config

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.BuildConfig
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcp.ui.theme.TitleDesign
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.percentage

const val TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN = "tag_number_text_field_config_screen"
const val TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN = "tag_password_text_field_config_screen"

@Composable
fun ConfigScreen(
    viewModel: ConfigViewModel,
    onNavMenuInicial: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ConfigContent(
                number = uiState.number,
                onNumberChanged = viewModel::onNumberChanged,
                password = uiState.password,
                onPasswordChanged = viewModel::onPasswordChanged,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                flagProgress = uiState.flagProgress,
                msgProgress = uiState.msgProgress,
                currentProgress = uiState.currentProgress,
                onSaveAndUpdate = viewModel::saveTokenAndUpdateAllDatabase,
                onNavMenuInicial = onNavMenuInicial,
                modifier = Modifier.padding(innerPadding),
            )
            viewModel.returnDataConfig()
            viewModel.updateVersion(BuildConfig.VERSION_NAME)
        }
    }
}

@Composable
fun ConfigContent(
    number: String,
    onNumberChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    msgProgress: String,
    currentProgress: Float,
    flagProgress: Boolean,
    onSaveAndUpdate: () -> Unit,
    onNavMenuInicial: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign("NRO APARELHO:")
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 24.sp
            ),
            value = number,
            onValueChange = onNumberChanged,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        TitleDesign("SENHA:")
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            textStyle = TextStyle(
                fontSize = 24.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Button(
            onClick = onSaveAndUpdate,
            modifier = Modifier.fillMaxWidth(),

        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_save_update))
        }
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Button(
            onClick = onNavMenuInicial,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
        }
        BackHandler {}

        if (flagProgress) {
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = msgProgress,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        if(flagDialog) {
            if(flagFailure){
                val text = when(errors){
                    Errors.FIELDEMPTY -> stringResource(id = R.string.text_field_empty_config)
                    Errors.TOKEN -> stringResource(id = R.string.text_recover_token, failure)
                    Errors.UPDATE -> stringResource(id = R.string.text_update_failure, failure)
                    Errors.EXCEPTION,
                    Errors.INVALID -> stringResource(id = R.string.text_failure, failure)
                }
                AlertDialogSimpleDesign(
                    text = text,
                    setCloseDialog = setCloseDialog,
                )
            } else {
                AlertDialogSimpleDesign(
                    text = stringResource(id = R.string.text_config_success),
                    setCloseDialog = setCloseDialog,
                    setActionButtonOK = onNavMenuInicial,
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewProgress() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELDEMPTY,
                failure = "",
                flagProgress = true,
                msgProgress = "Enviando Dados de Token",
                currentProgress = percentage(1f, 3f),
                onSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ConfigPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "16997417840",
                onNumberChanged = {},
                password = "12345",
                onPasswordChanged = {},
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELDEMPTY,
                failure = "",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewDialogOpenFieldEmpty() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.FIELDEMPTY,
                failure = "",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewDialogOpenFailureToken() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.TOKEN,
                failure = "Failure Usecase",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewDialogOpenFailureUpdate() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.UPDATE,
                failure = "Failure Usecase",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
