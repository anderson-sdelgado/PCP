package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.nroequip

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
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.lib.TypeEquip
import br.com.usinasantafe.pcp.presenter.theme.MsgUpdate
import br.com.usinasantafe.pcp.presenter.theme.Progress
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate

const val TAG_NUMBER_TEXT_FIELD_NRO_EQUIP_SCREEN = "tag_number_text_field_nro_equip_screen"

@Composable
fun NroEquipScreen(
    viewModel: NroEquipProprioViewModel = hiltViewModel(),
    onNavMovProprioList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavEquipSegList: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            NroEquipContent(
                nroEquip = uiState.text,
                flowApp = uiState.flowApp,
                typeEquip = uiState.typeEquip,
                onTextField = viewModel::onTextField,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavMovProprioList = onNavMovProprioList,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                onNavEquipSegList = onNavEquipSegList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun NroEquipContent(
    nroEquip: String,
    flowApp: FlowApp,
    typeEquip: TypeEquip,
    onTextField: (String, TypeButton) -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusStateUpdate,
    onNavMovProprioList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavEquipSegList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_nro_equip))
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
            value = nroEquip,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_NRO_EQUIP_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(setActionButton = onTextField)
        BackHandler {
            when (typeEquip) {
                TypeEquip.VEICULO -> {
                    when (flowApp) {
                        FlowApp.ADD -> onNavMovProprioList()
                        FlowApp.CHANGE -> onNavDetalheMovProprio()
                    }
                }

                TypeEquip.VEICULOSEG -> onNavEquipSegList()
            }
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
            when (typeEquip) {
                TypeEquip.VEICULO -> {
                    when (flowApp) {
                        FlowApp.ADD -> onNavEquipSegList()
                        FlowApp.CHANGE -> onNavDetalheMovProprio()
                    }
                }

                TypeEquip.VEICULOSEG -> onNavEquipSegList()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "310",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreviewFieldEmpty() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreviewFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreviewUpdate() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreviewBlockedAccess() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreviewUpdated() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
