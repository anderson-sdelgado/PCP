package br.com.usinasantafe.pcp.presenter.visitterc.cpf

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
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.initial.matricvigia.TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN
import br.com.usinasantafe.pcp.ui.theme.AlertDialogProgressDesign
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.ButtonsGenericNumeric
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TitleDesign
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeButton
import br.com.usinasantafe.pcp.utils.TypeOcupante

@Composable
fun CpfVisitTercScreen(
    viewModel: CpfVisitTercViewModel,
    onNavTipo: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavNome: (String) -> Unit,
    onNavPassagList: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            CpfVisitTercContent(
                flowApp = uiState.flowApp,
                typeOcupante = uiState.typeOcupante,
                title = uiState.title,
                cpf = uiState.cpf,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                flagProgress = uiState.flagProgress,
                msgProgress = uiState.msgProgress,
                currentProgress = uiState.currentProgress,
                setTextField = viewModel::setTextField,
                onNavTipo = onNavTipo,
                onNavDetalhe = onNavDetalhe,
                onNavNome = onNavNome,
                onNavPassagList = onNavPassagList,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.getCpf()
            viewModel.recoverTitle()
        }
    }
}

@Composable
fun CpfVisitTercContent(
    flowApp: FlowApp,
    typeOcupante: TypeOcupante,
    title: String,
    cpf: String,
    setTextField: (String, TypeButton) -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    flagProgress: Boolean,
    msgProgress: String,
    currentProgress: Float,
    onNavTipo: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavNome: (String) -> Unit,
    onNavPassagList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = title)
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Previous
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 28.sp
            ),
            readOnly = true,
            value = cpf,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(setActionButton = setTextField)
        BackHandler {
            when(typeOcupante) {
                TypeOcupante.MOTORISTA -> {
                    when (flowApp) {
                        FlowApp.ADD -> onNavTipo()
                        FlowApp.CHANGE -> onNavDetalhe()
                    }
                }
                TypeOcupante.PASSAGEIRO -> onNavPassagList()
            }

        }

        if (flagDialog) {
            val text = if (flagFailure) {
                when (errors) {
                    Errors.FIELDEMPTY -> stringResource(
                        id = R.string.text_field_empty,
                        stringResource(id = R.string.text_title_matric_colab)
                    )

                    Errors.UPDATE -> stringResource(id = R.string.text_update_failure, failure)
                    Errors.TOKEN,
                    Errors.EXCEPTION -> stringResource(id = R.string.text_failure, failure)

                    Errors.INVALID -> stringResource(
                        id = R.string.text_input_data_invalid,
                        stringResource(id = R.string.text_title_matric_colab)
                    )
                }
            } else {
                msgProgress
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagProgress) {
            AlertDialogProgressDesign(
                currentProgress = currentProgress,
                msgProgress = msgProgress
            )
        }

        if (flagAccess) {
            onNavNome(cpf)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CpfVisitTercPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CpfVisitTercContent(
                typeOcupante = TypeOcupante.MOTORISTA,
                flowApp = FlowApp.ADD,
                title = "CPF TERCEIRO",
                cpf = "123.456.789-00",
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELDEMPTY,
                failure = "",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onNavTipo = {},
                onNavDetalhe = {},
                onNavNome = {},
                onNavPassagList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}