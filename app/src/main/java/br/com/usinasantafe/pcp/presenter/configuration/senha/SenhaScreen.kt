package br.com.usinasantafe.pcp.presenter.configuration.senha

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcp.ui.theme.TitleDesign

const val TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN = "tag_password_text_field_senha_screen"

@Composable
fun SenhaScreen(
    viewModel: SenhaViewModel,
    onNavMenuInicial: () -> Unit,
    onNavConfig: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            SenhaContent(
                password = uiState.password,
                onPasswordChanged =  viewModel::updatePassword,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                failure = uiState.failure,
                flagAccess = uiState.flagAccess,
                onCheckAccess = viewModel::checkAccess,
                onNavMenuInicial = onNavMenuInicial,
                onNavConfig = onNavConfig,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Composable
fun SenhaContent(
    password: String,
    onPasswordChanged: (String) -> Unit,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    failure: String,
    flagAccess: Boolean,
    onCheckAccess: () -> Unit,
    onNavMenuInicial: () -> Unit,
    onNavConfig: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_password))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            textStyle = TextStyle(
                fontSize = 24.sp
            ),
            visualTransformation = PasswordVisualTransformation(),
            value = password,
            onValueChange = onPasswordChanged,
            modifier = Modifier.fillMaxWidth().testTag(TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        )  {
            Button(
                onClick = onCheckAccess,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
            Button(
                onClick = onNavMenuInicial,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
        }
        BackHandler {}

        if(flagDialog) {
            val text = if(!flagFailure) {
                stringResource(id = R.string.text_password_invalid)
            } else {
                stringResource(id = R.string.text_failure, failure)
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagAccess) {
            onNavConfig()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SenhaPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SenhaContent(
                password = "teste",
                onPasswordChanged = {},
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                failure = "",
                flagAccess = false,
                onCheckAccess = {},
                onNavMenuInicial = {},
                onNavConfig = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SenhaPagePreviewMsgOpen() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SenhaContent(
                password = "",
                onPasswordChanged = {},
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = false,
                failure = "",
                flagAccess = false,
                onCheckAccess = {},
                onNavMenuInicial = {},
                onNavConfig = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SenhaPagePreviewMsgOpenFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SenhaContent(
                password = "",
                onPasswordChanged = {},
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                failure = "Error CheckPasswordConfig",
                flagAccess = false,
                onCheckAccess = {},
                onNavMenuInicial = {},
                onNavConfig = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}