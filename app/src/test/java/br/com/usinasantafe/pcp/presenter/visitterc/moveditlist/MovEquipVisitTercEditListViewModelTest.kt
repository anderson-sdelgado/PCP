package br.com.usinasantafe.pcp.presenter.visitterc.moveditlist

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure // <<<--- IMPORT ADICIONADO
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.CloseAllMovVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetMovEquipVisitTercOpenList
import br.com.usinasantafe.pcp.presenter.visitterc.model.MovEquipVisitTercModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovEquipVisitTercEditListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // PADRONIZADO: Mocks como propriedades da classe
    private val getMovEquipVisitTercOpenList = mock<GetMovEquipVisitTercOpenList>()
    private val closeAllMovVisitTerc = mock<CloseAllMovVisitTerc>()

    // PADRONIZADO: Helper function para criar ViewModel
    private fun getViewModel() = MovEquipVisitTercEditListViewModel(
        getMovEquipVisitTercOpenList,
        closeAllMovVisitTerc
    )

    @Test
    fun `Check return failure if have error in GetMovEquipVisitTercOpenList`() = runTest {
        whenever(
            getMovEquipVisitTercOpenList()
        ).thenReturn(
            resultFailure( // PADRONIZADO: Usando resultFailure
                "GetMovEquipVisitTercOpenList",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel()

        viewModel.recoverMovEquipEditList()
        val state = viewModel.uiState.value // PADRONIZADO: Pegar o state para asserts

        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem padronizado
            "MovEquipVisitTercEditListViewModel.recoverMovEquipEditList -> GetMovEquipVisitTercOpenList -> java.lang.Exception", // PADRONIZADO: Formato da mensagem
            state.failure
        )
        assertEquals( // actual, expected (list) - Lista deve estar vazia
            state.movEquipVisitTercModelList,
            emptyList<MovEquipVisitTercModel>()
        )
        assertEquals( // actual, expected (boolean)
            state.flagCloseAllMov,
            false // Ação de fechar não foi chamada/sucedida
        )
    }

    @Test
    fun `Check return list if GetMovEquipVisitTercOpenList execute correctly`() = runTest {
        val expectedList = listOf(
            MovEquipVisitTercModel(
                id = 1,
                dthr = "08/08/2024 12:00",
                motorista = "Anderson da Silva Delgado",
                veiculo = "GOL",
                placa = "ABC-1234",
                tipoVisitTerc = "VISITANTE"
            )
        )
        whenever(
            getMovEquipVisitTercOpenList()
        ).thenReturn(
            Result.success(expectedList)
        )
        val viewModel = getViewModel()
        viewModel.recoverMovEquipEditList()
        val state = viewModel.uiState.value

        assertEquals(
            expectedList,
            state.movEquipVisitTercModelList
        )
        assertEquals(
            state.flagDialog,
            false
        )
        assertEquals(
            state.flagCloseAllMov,
            false
        )
    }

    @Test
    fun `Check return failure if have error in CloseAllMovVisitTercOpen`() = runTest {
        whenever(
            closeAllMovVisitTerc()
        ).thenReturn(
            resultFailure(
                "CloseAllMovVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel()

        viewModel.closeAllMov()
        val state = viewModel.uiState.value

        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            "MovEquipVisitTercEditListViewModel.closeAllMov -> CloseAllMovVisitTerc -> java.lang.Exception", // PADRONIZADO: Formato da mensagem
            state.failure
        )
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (boolean)
            state.flagCloseAllMov,
            false // Ação de fechar falhou
        )
        // A lista pode ou não ter sido carregada antes, então não verificamos aqui
    }

    @Test
    fun `Check return true if CloseAllMovVisitTercOpen execute correctly`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            closeAllMovVisitTerc()
        ).thenReturn(
            Result.success(true)
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel()

        // Act
        viewModel.closeAllMov()
        val state = viewModel.uiState.value // PADRONIZADO: Pegar o state para asserts

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagCloseAllMov,
            true // Ação de fechar sucedeu
        )
        assertEquals( // actual, expected (boolean) - Verificação de ausência de erro
            state.flagDialog,
            false
        )
        
        // A lista pode ou não ter sido carregada antes, então não verificamos aqui
    }
}
