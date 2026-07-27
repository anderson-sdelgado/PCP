package br.com.usinasantafe.pcp.presenter.proprio.nroequip

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.common.CheckNroEquip
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetNroEquipProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SetIdEquipProprio
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableEquip
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.lib.TypeEquip
import br.com.usinasantafe.pcp.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class NroEquipProprioViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkNroEquip = mock<CheckNroEquip>()
    private val setIdEquipProprio = mock<SetIdEquipProprio>()
    private val updateTableEquip = mock<UpdateTableEquip>()
    private val getNroEquipProprio = mock<GetNroEquipProprio>()
    private fun getViewModel(
        savedStateHandle: SavedStateHandle
    ) = NroEquipProprioViewModel(
        savedStateHandle,
        checkNroEquip,
        setIdEquipProprio,
        updateTableEquip,
        getNroEquipProprio
    )

    @Test
    fun `Check add char in nroEquip`() {
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.nroEquip,
            "19759"
        )
    }

    @Test
    fun `Check remover and add char in nroEquip`() {
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.nroEquip,
            "191"
        )
    }

    @Test
    fun `Check view msg if field is empty`() {
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.errors,
            Errors.FIELD_EMPTY
        )
    }

    @Test
    fun `Check return failure if have error in CheckNroEquipProprio`() = runTest {
        whenever(
            checkNroEquip("100")
        ).thenReturn(
            resultFailure(
                "CheckNroEquip",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField(
            "100",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.errors,
            Errors.EXCEPTION
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "NroEquipProprioViewModel.setNroEquip -> CheckNroEquip -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return false if matric is invalid`() = runTest {
        whenever(
            checkNroEquip("100")
        ).thenReturn(
            resultFailure(
                "CheckNroEquip",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField(
            "100",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "NroEquipProprioViewModel.setNroEquip -> CheckNroEquip -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if nroEquip is valid`() = runTest {
        whenever(
            checkNroEquip("100")
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField(
            "100",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            true
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            false
        )
    }

    @Test
    fun `Check return failure if have error in SetEquipProprio `() = runTest {
        whenever(
            checkNroEquip("100")
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            setIdEquipProprio(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "SetIdEquipProprio",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField(
            "100",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.errors,
            Errors.EXCEPTION
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "NroEquipProprioViewModel.setNroEquip -> SetIdEquipProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if nroEquip is valid and setEquipProprio execute success`() = runTest {
        whenever(
            checkNroEquip("100")
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            setIdEquipProprio(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField(
            "100",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            true
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            false
        )
    }

    @Test
    fun `Check return failure datasource if have error in usecase CleanEquip is datasource`() =
        runTest {
            whenever(
                updateTableEquip(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_equip",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanEquip -> java.lang.NullPointerException",
                        msgProgress = "CleanEquip -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                        Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                        Args.ID_ARGS to 0
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                NroEquipProprioState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                NroEquipProprioState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "NroEquipProprioViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException",
                    msgProgress = "NroEquipProprioViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase in setTextField if have error in usecase CleanEquip`() =
        runTest {
            whenever(
                updateTableEquip(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_equip",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanEquip -> java.lang.NullPointerException",
                        msgProgress = "CleanEquip -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                        Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                        Args.ID_ARGS to 0
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                NroEquipProprioState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                NroEquipProprioState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "NroEquipProprioViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException",
                    msgProgress = "NroEquipProprioViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField("ATUALIZAR DADOS", TypeButton.UPDATE)
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "NroEquipProprioViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return success in updateAllDatabase if all update run correctly`() = runTest {
        whenever(
            updateTableEquip(
                count = 1f,
                sizeAll = 4f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(1f, 4f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = percentage(2f, 4f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = percentage(3f, 4f)
                ),
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            NroEquipProprioState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = percentage(1f, 4f)
            )
        )
        assertEquals(
            result[1],
            NroEquipProprioState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                currentProgress = percentage(2f, 4f),
            )
        )
        assertEquals(
            result[2],
            NroEquipProprioState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_equip",
                currentProgress = percentage(3f, 4f),
            )
        )
        assertEquals(
            result[3],
            NroEquipProprioState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `Check return success if setTextField update is success`() = runTest {
        whenever(
            updateTableEquip(
                count = 1f,
                sizeAll = 4f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(1f, 4f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = percentage(2f, 4f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = percentage(3f, 4f)
                ),
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField(
            "ATUALIZAR DADOS",
            TypeButton.UPDATE
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Atualização de dados realizado com sucesso!"
        )
    }

    @Test
    fun `check return failure usecase if have error in usecase GetNroEquip`() = runTest {
        whenever(
            getNroEquipProprio(1)
        ).thenReturn(
            resultFailure(
                "GetNroEquip",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )
        viewModel.getNroEquip()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "NroEquipProprioViewModel.getNroEquip -> GetNroEquip -> java.lang.Exception"
        )
    }

    @Test
    fun `check return nroEquip if GetNroEquip execute success`() = runTest {
        whenever(
            getNroEquipProprio(1)
        ).thenReturn(
            Result.success("100")
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )
        viewModel.getNroEquip()
        assertEquals(
            viewModel.uiState.value.nroEquip,
            "100"
        )
    }
}