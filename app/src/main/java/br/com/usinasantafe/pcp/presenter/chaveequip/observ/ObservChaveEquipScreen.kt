package br.com.usinasantafe.pcp.presenter.chaveequip.observ

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.chave.observ.TAG_OBSERV_TEXT_FIELD_CHAVE
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcp.ui.theme.TitleDesign
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovKey

@Composable
fun ObservChaveEquipScreen(
    viewModel: ObservChaveEquipViewModel,
    onNavMatricColab: () -> Unit,
    onNavControleList: () -> Unit,
    onNavDetalhe: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ObservChaveEquipContent(
                flowApp = uiState.flowApp,
                typeMov = uiState.typeMov,
                observ = uiState.observ,
                onObservChanged = viewModel::onObservChanged,
                setObserv = viewModel::setObserv,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavMatricColab = onNavMatricColab,
                onNavMovChaveList = onNavControleList,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.getObserv()
        }
    }
}

@Composable
fun ObservChaveEquipContent(
    flowApp: FlowApp,
    typeMov: TypeMovKey,
    observ: String?,
    onObservChanged: (String) -> Unit,
    setObserv: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavMatricColab: () -> Unit,
    onNavMovChaveList: () -> Unit,
    onNavDetalhe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_observ
            )
        )
        Spacer(
            modifier = Modifier.padding(
                vertical = 8.dp
            )
        )
        OutlinedTextField(
            value = if(observ.isNullOrEmpty()) "" else observ,
            onValueChange = onObservChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(TAG_OBSERV_TEXT_FIELD_CHAVE),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 28.sp
            ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    when (flowApp) {
                        FlowApp.ADD -> {
                            when(typeMov){
                                TypeMovKey.REMOVE -> onNavMovChaveList()
                                TypeMovKey.RECEIPT -> onNavMatricColab()
                            }
                        }
                        FlowApp.CHANGE -> onNavDetalhe()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = setObserv,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}

        if(flagDialog){
            val text = stringResource(id = R.string.text_failure, failure)
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagAccess){
            when(flowApp){
                FlowApp.ADD -> onNavMovChaveList()
                FlowApp.CHANGE -> onNavDetalhe()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ObservChaveEquipPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ObservChaveEquipContent(
                flowApp = FlowApp.ADD,
                typeMov = TypeMovKey.REMOVE,
                observ = "Teste",
                onObservChanged = {},
                setObserv = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavMatricColab = {},
                onNavMovChaveList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}