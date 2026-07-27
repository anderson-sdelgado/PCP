package br.com.usinasantafe.pcp.presenter.proprio.passaglist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.CleanPassagColab
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.DeletePassagColab
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetPassagColabList
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Import necessário
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PassagColabListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val cleanPassagColab = mock<CleanPassagColab>()
    private val getPassagColabList = mock<GetPassagColabList>()
    private val deletePassagColab = mock<DeletePassagColab>()

    private fun createViewModel(
        savedStateHandle: SavedStateHandle
    ) = PassagColabListViewModel(
        savedStateHandle,
        cleanPassagColab,
        getPassagColabList,
        deletePassagColab
    )

    @Test
    fun `Check return failure if CleanPassagColab have failure`() = runTest {
        whenever(
            cleanPassagColab()
        ).thenReturn(
            resultFailure(
                "CleanPassagColab",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.cleanPassag()
        val state = viewModel.uiState.value
        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            state.failure,
            "PassagColabListViewModel.cleanPassag -> CleanPassagColab -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have failure in GetPassagColabList`() = runTest {
        whenever(
            getPassagColabList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            resultFailure(
                "GetPassagColabList",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.recoverPassag()
        val state = viewModel.uiState.value
        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            state.failure,
            "PassagColabListViewModel.recoverPassag -> GetPassagColabList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list Colab if GetPassagColabList execute successfully`() = runTest {
        whenever(
            getPassagColabList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    Colab(
                        matricColab = 19759,
                        nomeColab = "ANDERSON DA SILVA DELGADO"
                    ),
                    Colab(
                        matricColab = 19035,
                        nomeColab = "JOSE DONIZETE"
                    )
                )
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.recoverPassag()
        val state = viewModel.uiState.value
        assertEquals(
            state.passagList.size,
            2
        )
        assertEquals(
            state.passagList[0].matricColab,
            19759
        )
        assertEquals(
            state.flagDialog,
            false
        )
        
    }

    @Test
    fun `Check return failure if have failure in DeletePassagColab`() = runTest {
        whenever(
            deletePassagColab(
                19759,
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            resultFailure(
                "DeletePassagColab",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setDelete(19759)
        assertEquals(
            viewModel.uiState.value.flagDialogCheck,
            true
        )
        viewModel.deletePassag()
        val state = viewModel.uiState.value
        assertEquals(
            state.flagDialogCheck,
            false
        )
        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            state.failure,
            "PassagColabListViewModel.deletePassag -> DeletePassagColab -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list Colab after deletePassag execute successfully`() = runTest {
        whenever(
            deletePassagColab(
                19759,
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getPassagColabList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    Colab(
                        matricColab = 19035,
                        nomeColab = "JOSE DONIZETE"
                    )
                )
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setDelete(19759)
        assertEquals(
            viewModel.uiState.value.flagDialogCheck,
            true
        )
        viewModel.deletePassag()
        val state = viewModel.uiState.value
        assertEquals(
            state.flagDialogCheck,
            false
        )
        assertEquals(
            state.flagDialog,
            false
        )
        assertEquals(
            state.passagList.size,
            1
        )
        assertEquals(
            state.passagList[0].matricColab,
            19035
        )
        
    }

}
