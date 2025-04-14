package br.com.usinasantafe.pcp.presenter.proprio.passaglist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.usecases.proprio.CleanPassagColab
import br.com.usinasantafe.pcp.domain.usecases.proprio.DeletePassagColab
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetPassagColabList
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeOcupante
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

    // PADRONIZADO: Mocks como propriedades da classe
    private val cleanPassagColab = mock<CleanPassagColab>()
    private val getPassagColabList = mock<GetPassagColabList>()
    private val deletePassagColab = mock<DeletePassagColab>()

    // PADRONIZADO: Helper function para criar ViewModel
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
        // PADRONIZADO: Mocks não são mais criados aqui
        whenever(
            cleanPassagColab()
        ).thenReturn(
            resultFailure(
                "CleanPassagColab",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando createViewModel
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
        val state = viewModel.uiState.value // PADRONIZADO: Usando state
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "PassagColabListViewModel.cleanPassag -> CleanPassagColab -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have failure in GetPassagColabList`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
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
        // PADRONIZADO: Usando createViewModel
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
        val state = viewModel.uiState.value // PADRONIZADO: Usando state
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "PassagColabListViewModel.recoverPassag -> GetPassagColabList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list Colab if GetPassagColabList execute successfully`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
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
        // PADRONIZADO: Usando createViewModel
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
        val state = viewModel.uiState.value // PADRONIZADO: Usando state
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.passagList.size,
            2
        )
        assertEquals( // Padronizado
            state.passagList[0].matricColab,
            19759
        )
        // PADRONIZADO: Verificar ausência de erro
        assertEquals( // Padronizado
            state.flagDialog,
            false
        )
        
    }

    @Test
    fun `Check return failure if have failure in DeletePassagColab`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
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
        // PADRONIZADO: Usando createViewModel
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
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            viewModel.uiState.value.flagDialogCheck, // Mantido viewModel aqui
            true
        )
        viewModel.deletePassag()
        val state = viewModel.uiState.value // PADRONIZADO: Usando state
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialogCheck,
            false
        )
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "PassagColabListViewModel.deletePassag -> DeletePassagColab -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list Colab after deletePassag execute successfully`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
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
                        matricColab = 19035, // Apenas o passageiro restante
                        nomeColab = "JOSE DONIZETE"
                    )
                )
            )
        )
        // PADRONIZADO: Usando createViewModel
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
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            viewModel.uiState.value.flagDialogCheck, // Mantido viewModel aqui
            true
        )
        viewModel.deletePassag()
        val state = viewModel.uiState.value // PADRONIZADO: Usando state
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialogCheck,
            false
        )
        assertEquals( // Padronizado
            state.flagDialog,
            false
        )
        assertEquals( // Padronizado
            state.passagList.size,
            1
        )
        assertEquals( // Padronizado
            state.passagList[0].matricColab,
            19035
        )
        
    }

}
