package br.com.usinasantafe.pcp.presenter.view.chaveEquip.nroequip

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
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.nroequip.TAG_NUMBER_TEXT_FIELD_NRO_EQUIP_SCREEN
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.presenter.theme.MsgUpdate
import br.com.usinasantafe.pcp.presenter.theme.Progress
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate

@Composable
fun NroEquipChaveEquipScreen(
    viewModel: NroEquipChaveEquipViewModel = hiltViewModel(),
    onNavControleList: () -> Unit,
    onNavMatricColab: () -> Unit,
    onNavDetalhe: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recover()
            }

            NroEquipChaveEquipContent(
                text = uiState.text,
                flowApp = uiState.flowApp,
                onTextField = viewModel::onTextField,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavMatricColab = onNavMatricColab,
                onNavControleList = onNavControleList,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun NroEquipChaveEquipContent(
    text: String,
    flowApp: FlowApp,
    onTextField: (String, TypeButton) -> Unit,
    onCloseDialog: () -> Unit,
    status: UiStatusStateUpdate,
    onNavControleList: () -> Unit,
    onNavMatricColab: () -> Unit,
    onNavDetalhe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_nro_equip
            )
        )
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
            value = text,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_NRO_EQUIP_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(setActionButton = onTextField)
        BackHandler {
            when (flowApp) {
                FlowApp.ADD -> onNavControleList()
                FlowApp.CHANGE -> onNavDetalhe()
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
            when (flowApp) {
                FlowApp.ADD -> onNavMatricColab()
                FlowApp.CHANGE -> onNavDetalhe()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NroEquipChaveEquipPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipChaveEquipContent(
                text = "",
                flowApp = FlowApp.ADD,
                onTextField = { _, _ -> },
                onCloseDialog = {},
                status = UiStatusStateUpdate(),
                onNavControleList = {},
                onNavMatricColab = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}