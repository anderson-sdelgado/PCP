package br.com.usinasantafe.pcp.presenter.residencia.moveditlist

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.CloseAllMovResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetMovEquipResidenciaOpenList
import br.com.usinasantafe.pcp.presenter.residencia.model.MovEquipResidenciaModel
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Adicionado/Confirmado
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovEquipResidenciaEditListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Mocks movidos para fora das funções de teste
    private val getMovEquipResidenciaOpenList = mock<GetMovEquipResidenciaOpenList>()
    private val closeAllMovResidencia = mock<CloseAllMovResidencia>()

    // Função helper para criar o ViewModel
    private fun createViewModel() = MovEquipResidenciaEditListViewModel(
        getMovEquipResidenciaOpenList,
        closeAllMovResidencia
    )

    @Test
    fun `Check return failure if have error in GetMovEquipResidenciaOpenList`() = runTest {
        whenever(
            getMovEquipResidenciaOpenList()
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "GetMovEquipResidenciaOpenList",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel()
        viewModel.recoverMovEquipEditList()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "MovEquipResidenciaEditListViewModel.recoverMovEquipEditList -> GetMovEquipResidenciaOpenList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list if GetMovEquipResidenciaOpenList execute correctly`() = runTest {
        val list = listOf(
            MovEquipResidenciaModel(
                id = 1,
                dthr = "08/08/2024 12:00",
                motorista = "Anderson da Silva Delgado",
                veiculo = "GOL",
                placa = "ABC-1234"
            )
        )
        whenever(getMovEquipResidenciaOpenList()).thenReturn(
            Result.success(list)
        )
        val viewModel = createViewModel()
        viewModel.recoverMovEquipEditList()
        val state = viewModel.uiState.value
        val listMov = state.movEquipResidenciaModelList
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            false // Garante que não houve diálogo de erro
        )
        assertEquals( // Padronizado
            listMov.size,
            1
        )
        assertEquals( // Padronizado
            listMov[0].veiculo,
            "GOL"
        )
        assertEquals( // Padronizado
            listMov,
            list
        )
        
    }

    @Test
    fun `Check return failure if have error in CloseAllMovResidencia`() = runTest { // Nome do teste ajustado
        whenever(
            closeAllMovResidencia()
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "CloseAllMovResidencia",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel()
        viewModel.closeAllMov()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "MovEquipResidenciaEditListViewModel.closeAllMov -> CloseAllMovResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if CloseAllMovResidencia execute correctly`() = runTest { // Nome do teste ajustado
        whenever(
            closeAllMovResidencia()
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = createViewModel()
        viewModel.closeAllMov()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagCloseAllMov,
            true
        )
        assertEquals( // Padronizado
            state.flagDialog,
            false // Garante que não houve diálogo de erro
        )
        
    }
}
