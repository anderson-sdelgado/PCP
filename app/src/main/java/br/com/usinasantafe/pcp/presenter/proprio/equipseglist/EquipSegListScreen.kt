package br.com.usinasantafe.pcp.presenter.proprio.equipseglist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.ui.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcp.ui.theme.PCPTheme
import br.com.usinasantafe.pcp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcp.ui.theme.TitleDesign
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeEquip

@Composable
fun EquipSegListScreen(
    viewModel: EquipSegListViewModel,
    onNavDetalheMovProprio: () -> Unit,
    onNavNroEquip: (Int) -> Unit,
    onNavMatricColab: () -> Unit,
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            EquipSegListContent(
                equipSegList = uiState.equipSegList,
                flowApp = uiState.flowApp,
                setDelete = viewModel::setDelete,
                flagDialogCheck = uiState.flagDialogCheck,
                setCloseDialogCheck = viewModel::setCloseDialogCheck,
                setDeleteEquipSeg = viewModel::deleteVeicSeg,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                onNavNroEquip = onNavNroEquip,
                onNavMatricColab = onNavMatricColab,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.cleanVeicSeg()
            viewModel.recoverVeicSeg()
        }
    }
}

@Composable
fun EquipSegListContent(
    equipSegList: List<Equip>,
    flowApp: FlowApp,
    setDelete: (Int) -> Unit,
    flagDialogCheck: Boolean,
    setCloseDialogCheck: () -> Unit,
    setDeleteEquipSeg: () -> Unit,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavDetalheMovProprio: () -> Unit,
    onNavNroEquip: (Int) -> Unit,
    onNavMatricColab: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_equip_seg))
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(equipSegList) { equip ->
                ItemListDesign(
                    text = "${equip.nroEquip} - ${equip.descrEquip}",
                    setActionItem = { setDelete(equip.idEquip) },
                    id = equip.idEquip
                )
            }
        }
        Button(
            onClick = { onNavNroEquip(TypeEquip.VEICULOSEG.ordinal) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_insert))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    when (flowApp) {
                        FlowApp.ADD -> onNavNroEquip(TypeEquip.VEICULO.ordinal)
                        FlowApp.CHANGE -> onNavDetalheMovProprio()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = {
                    when (flowApp) {
                        FlowApp.ADD -> onNavMatricColab()
                        FlowApp.CHANGE -> onNavDetalheMovProprio()
                    }
                },
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagDialogCheck) {
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_delete_equip_seg),
                setCloseDialog = setCloseDialogCheck,
                setActionButtonOK = setDeleteEquipSeg
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun EquipSegListPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            EquipSegListContent(
                equipSegList = emptyList(),
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = false,
                setCloseDialogCheck = {},
                setDeleteEquipSeg = {},
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavDetalheMovProprio = {},
                onNavNroEquip = {},
                onNavMatricColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EquipSegListPagePreviewList() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            EquipSegListContent(
                equipSegList = listOf(
                    Equip(
                        idEquip = 10,
                        nroEquip = 100,
                        "TRATOR"
                    ),
                    Equip(
                        idEquip = 20,
                        nroEquip = 200,
                        "CAMINHÃO"
                    )
                ),
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = false,
                setCloseDialogCheck = {},
                setDeleteEquipSeg = {},
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavDetalheMovProprio = {},
                onNavNroEquip = {},
                onNavMatricColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EquipSegListPagePreviewFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            EquipSegListContent(
                equipSegList = listOf(
                    Equip(
                        idEquip = 10,
                        nroEquip = 100,
                        "TRATOR"
                    ),
                    Equip(
                        idEquip = 20,
                        nroEquip = 200,
                        "CAMINHÃO"
                    )
                ),
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = false,
                setCloseDialogCheck = {},
                setDeleteEquipSeg = {},
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure Usecase",
                onNavDetalheMovProprio = {},
                onNavNroEquip = {},
                onNavMatricColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EquipSegListPagePreviewMsgDelete() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            EquipSegListContent(
                equipSegList = listOf(
                    Equip(
                        idEquip = 10,
                        nroEquip = 100,
                        "TRATOR"
                    ),
                    Equip(
                        idEquip = 20,
                        nroEquip = 200,
                        "CAMINHÃO"
                    )
                ),
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = true,
                setCloseDialogCheck = {},
                setDeleteEquipSeg = {},
                flagDialog = false,
                setCloseDialog = {},
                failure = "Failure Usecase",
                onNavDetalheMovProprio = {},
                onNavNroEquip = {},
                onNavMatricColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}