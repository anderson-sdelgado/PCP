package br.com.usinasantafe.pcp.presenter.chaveequip.nomecolab

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.SetMatricColabMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.StartRemoveMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.common.GetNomeColab
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class NomeColabChaveEquipViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getNomeColab = mock<GetNomeColab>()
    private val setMatricColabMovChaveEquip = mock<SetMatricColabMovChaveEquip>()
    private val startRemoveMovChaveEquip = mock<StartRemoveMovChaveEquip>()
    private fun getViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.MATRIC_COLAB_ARGS to "19759",
                Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                Args.TYPE_MOV_ARGS to TypeMovKey.RECEIPT.ordinal,
                Args.ID_ARGS to 0
            )
        )
    ) = NomeColabChaveEquipViewModel(
        savedStateHandle,
        getNomeColab,
        setMatricColabMovChaveEquip,
        startRemoveMovChaveEquip
    )


    @Test
    fun `returnNomeColab - Check return failure if have error in GetNomeColab`() =
        runTest {
            whenever(
                getNomeColab("19759")
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetNomeColab",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.returnNomeColab()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> GetNomeColab -> java.lang.Exception"
            )
        }

    @Test
    fun `returnNomeColab - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getNomeColab("19759")
            ).thenReturn(
                Result.success("ANDERSON DA SILVA DELGADO")
            )
            val viewModel = getViewModel()
            viewModel.returnNomeColab()
            assertEquals(
                viewModel.uiState.value.nomeColab,
                "ANDERSON DA SILVA DELGADO"
            )
        }

    @Test
    fun `setMatricColab - Check return failure if have error in SetMatricColab - RECEIPT - ADD`() =
        runTest {
            whenever(
                setMatricColabMovChaveEquip(
                    matricColab = "19759",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "ISetMatricColabMovChaveEquip",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.setMatricColab()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> ISetMatricColabMovChaveEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully - RECEIPT - ADD`() =
        runTest {
            whenever(
                setMatricColabMovChaveEquip(
                    matricColab = "19759",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getViewModel()
            viewModel.setMatricColab()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setMatricColab - Check return failure if have error in StartReceiptChave - REMOVE - ADD`() =
        runTest {
            whenever(
                startRemoveMovChaveEquip(
                    1
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "IStartReceiptChaveEquip",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.MATRIC_COLAB_ARGS to "19759",
                        Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                        Args.TYPE_MOV_ARGS  to TypeMovKey.RECEIPT.ordinal,
                        Args.ID_ARGS to 1
                    )
                )
            )
            viewModel.setMatricColab()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> IStartReceiptChaveEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `setMatricColab - Check return failure if have error in SetMatricColab - REMOVE - ADD`() =
        runTest {
            whenever(
                startRemoveMovChaveEquip(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setMatricColabMovChaveEquip(
                    matricColab = "19759",
                    flowApp = FlowApp.ADD,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "ISetMatricColabMovChave",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.MATRIC_COLAB_ARGS to "19759",
                        Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                        Args.TYPE_MOV_ARGS  to TypeMovKey.RECEIPT.ordinal,
                        Args.ID_ARGS to 1
                    )
                )
            )
            viewModel.setMatricColab()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> ISetMatricColabMovChave -> java.lang.Exception"
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully - REMOVE - ADD`() =
        runTest {
            whenever(
                startRemoveMovChaveEquip(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setMatricColabMovChaveEquip(
                    matricColab = "19759",
                    flowApp = FlowApp.ADD,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.MATRIC_COLAB_ARGS to "19759",
                        Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                        Args.TYPE_MOV_ARGS  to TypeMovKey.REMOVE.ordinal,
                        Args.ID_ARGS to 1
                    )
                )
            )
            viewModel.setMatricColab()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}