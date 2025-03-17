package br.com.usinasantafe.pcp.presenter.chaveequip.nroequip

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.GetNroEquipMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.SetIdEquipMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.common.CheckNroEquip
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateEquip
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeButton
import br.com.usinasantafe.pcp.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class NroEquipChaveEquipViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkNroEquip = mock<CheckNroEquip>()
    private val setIdEquipMovChaveEquip = mock<SetIdEquipMovChaveEquip>()
    private val updateEquip = mock<UpdateEquip>()
    private val getNroEquipMovChaveEquip = mock<GetNroEquipMovChaveEquip>()

    private fun getViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                Args.ID_ARGS to 0
            )
        )
    ) = NroEquipChaveEquipViewModel(
        savedStateHandle,
        checkNroEquip,
        setIdEquipMovChaveEquip,
        updateEquip,
        getNroEquipMovChaveEquip
    )

    @Test
    fun `Check add numeric with click in buttons`() =
        runTest {
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            assertEquals(
                viewModel.uiState.value.nroEquip,
                "19759"
            )
        }

    @Test
    fun `Check clean if click in button clear`() =
        runTest {
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.CLEAN)
            viewModel.setTextField("", TypeButton.CLEAN)
            assertEquals(
                viewModel.uiState.value.nroEquip,
                "197"
            )
        }

    @Test
    fun `Check return failure if click in button ok and nroEquip is empty`() =
        runTest {
            val viewModel = getViewModel()
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.FIELDEMPTY
            )
        }

    @Test
    fun `Check return failure usecase in setTextField if have error in usecase UpdateEquip`() =
        runTest {
            whenever(
                updateEquip(
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
                        failure = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = getViewModel()
            val result = viewModel.updateAllDatabase().toList()
            Assert.assertEquals(result.count(), 2)
            Assert.assertEquals(
                result[0],
                NroEquipChaveEquipState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(1f, 4f)
                )
            )
            Assert.assertEquals(
                result[1],
                NroEquipChaveEquipState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField("ATUALIZAR DADOS", TypeButton.UPDATE)
            Assert.assertEquals(
                viewModel.uiState.value.msgProgress,
                "Failure Usecase -> CleanEquip -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return success in updateAllDatabase if all update run correctly`() =
        runTest {
            whenever(
                updateEquip(
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
            val viewModel = getViewModel()
            val result = viewModel.updateAllDatabase().toList()
            Assert.assertEquals(result.count(), 4)
            Assert.assertEquals(
                result[0],
                NroEquipChaveEquipState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(1f, 4f)
                )
            )
            Assert.assertEquals(
                result[1],
                NroEquipChaveEquipState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = percentage(2f, 4f),
                )
            )
            Assert.assertEquals(
                result[2],
                NroEquipChaveEquipState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = percentage(3f, 4f),
                )
            )
            Assert.assertEquals(
                result[3],
                NroEquipChaveEquipState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    msgProgress = "Atualização de dados realizado com sucesso!",
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField("ATUALIZAR DADOS", TypeButton.UPDATE)
            Assert.assertEquals(viewModel.uiState.value.flagDialog, true)
            Assert.assertEquals(
                viewModel.uiState.value.msgProgress,
                "Atualização de dados realizado com sucesso!"
            )
        }

    @Test
    fun `setNroEquip - Check return failure if have error in CheckNroEquip`() =
        runTest {
            whenever(
                checkNroEquip("19759")
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CheckNroEquip",
                        cause = NullPointerException()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> CheckNroEquip -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `setNroEquip - Check return false if function execute successfully but access is denied`() =
        runTest {
            whenever(
                checkNroEquip("19759")
            ).thenReturn(
                Result.success(false)
            )
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
        }

    @Test
    fun `setNroEquip - Check return failure if have error in SetIdEquipMovChaveEquip`() =
        runTest {
            whenever(
                checkNroEquip("19759")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setIdEquipMovChaveEquip(
                    nroEquip = "19759",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SetIdEquipMovChaveEquip",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> SetIdEquipMovChaveEquip -> java.lang.Exception"
            )
        }
    @Test
    fun `setNroEquip - Check return true if function execute successfully and free access`() =
        runTest {
            whenever(
                checkNroEquip("19759")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setIdEquipMovChaveEquip(
                    nroEquip = "19759",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
        }
    @Test
    fun `getNroEquip - Check return failure if have error in GetNroEquipMovChaveEquip`() =
        runTest {
            whenever(
                getNroEquipMovChaveEquip(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetMatricColabMovChave",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
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
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> GetMatricColabMovChave -> java.lang.Exception"
            )
        }

    @Test
    fun `getNroEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getNroEquipMovChaveEquip(1)
            ).thenReturn(
                Result.success("19759")
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                        Args.ID_ARGS to 1
                    )
                )
            )
            viewModel.getNroEquip()
            assertEquals(
                viewModel.uiState.value.nroEquip,
                "19759"
            )
            assertEquals(
                viewModel.uiState.value.checkGetMatricColab,
                false
            )
        }
}