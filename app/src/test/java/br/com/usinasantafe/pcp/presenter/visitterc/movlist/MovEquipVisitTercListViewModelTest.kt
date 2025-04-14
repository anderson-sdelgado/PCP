package br.com.usinasantafe.pcp.presenter.visitterc.movlist

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.visitterc.GetMovEquipVisitTercInsideList
import br.com.usinasantafe.pcp.domain.usecases.visitterc.StartInputMovEquipVisitTerc
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import br.com.usinasantafe.pcp.presenter.visitterc.model.MovEquipVisitTercModel
import br.com.usinasantafe.pcp.utils.Errors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovEquipVisitTercListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Mocks como propriedades da classe (já estava padronizado)
    private val getHeader = mock<GetHeader>()
    private val getMovEquipVisitTercInsideList = mock<GetMovEquipVisitTercInsideList>()
    private val startInputMovEquipVisitTerc = mock<StartInputMovEquipVisitTerc>()

    // Helper function para criar ViewModel (já estava padronizado)
    private fun getViewModel() = MovEquipVisitTercListViewModel(
        getHeader,
        getMovEquipVisitTercInsideList,
        startInputMovEquipVisitTerc
    )

    @Test
    fun `Check return failure if have error in getHeader`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            getHeader()
        ).thenReturn(
            resultFailure( // PADRONIZADO: Usando resultFailure
                "GetHeader",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel()

        // Act
        viewModel.returnHeader()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem padronizado
            "MovEquipVisitTercListViewModel.returnHeader -> GetHeader -> java.lang.Exception",
            state.failure
        )
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.descrVigia,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.descrLocal,
            ""
        )
        assertEquals( // actual, expected (list) - Estado inicial/erro
            state.movEquipVisitTercModelList,
            emptyList<MovEquipVisitTercModel>()
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return Nome Vigia if getHeader execute correctly`() = runTest {
        // Arrange
        val expectedHeader = HeaderModel( // PADRONIZADO: Nome da variável mais descritivo
            descrVigia = "19759 - Anderson da Silva Delgado",
            descrLocal = "1 - Usina"
        )
        // PADRONIZADO: whenever multi-linha
        whenever(
            getHeader()
        ).thenReturn(
            Result.success(expectedHeader)
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel()

        // Act
        viewModel.returnHeader()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // expected, actual (string)
            expectedHeader.descrVigia,
            state.descrVigia
        )
        assertEquals( // expected, actual (string)
            expectedHeader.descrLocal,
            state.descrLocal
        )
        assertEquals( // actual, expected (boolean) - Verificação de ausência de erro
            state.flagDialog,
            false
        )
        
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (list) - Estado inicial
            state.movEquipVisitTercModelList,
            emptyList<MovEquipVisitTercModel>()
        )
        assertEquals( // actual, expected (boolean) - Estado inicial
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return failure if have error in recoverMovEquipList`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            getMovEquipVisitTercInsideList()
        ).thenReturn(
            resultFailure( // PADRONIZADO: Usando resultFailure
                "GetMovEquipVisitTercInsideList",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel()

        // Act
        viewModel.recoverMovEquipList()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem padronizado
            "MovEquipVisitTercListViewModel.recoverMovEquipList -> GetMovEquipVisitTercInsideList -> java.lang.Exception",
            state.failure
        )
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.descrVigia,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.descrLocal,
            ""
        )
        assertEquals( // actual, expected (list) - Estado inicial/erro
            state.movEquipVisitTercModelList,
            emptyList<MovEquipVisitTercModel>()
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return list if recoverMovEquipList execute correctly`() = runTest {
        // Arrange
        val expectedList = listOf( // PADRONIZADO: Nome da variável mais descritivo
            MovEquipVisitTercModel(
                id = 1,
                dthr = "DATA/HORA: 08/08/2024 12:00",
                motorista = "MOTORISTA: 326.949.728-88 - ANDERSON DA SILVA DELGADO",
                veiculo = "VEÍCULO: GOL",
                placa = "PLACA: ABC1234",
                tipoVisitTerc = "VISITANTE"
            )
        )
        // PADRONIZADO: whenever multi-linha
        whenever(
            getMovEquipVisitTercInsideList()
        ).thenReturn(
            Result.success(expectedList)
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel()

        // Act
        viewModel.recoverMovEquipList()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // expected, actual (list) - Comparar a lista inteira
            expectedList,
            state.movEquipVisitTercModelList
        )
        assertEquals( // actual, expected (boolean) - Verificação de ausência de erro
            state.flagDialog,
            false
        )
        
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado inicial
            state.descrVigia,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial
            state.descrLocal,
            ""
        )
        assertEquals( // actual, expected (boolean) - Estado inicial
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return failure if have error in startMov`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            startInputMovEquipVisitTerc()
        ).thenReturn(
            resultFailure( // PADRONIZADO: Usando resultFailure
                "StartInputMovEquipVisitTerc",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel()

        // Act
        viewModel.startMov()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem padronizado
            "MovEquipVisitTercListViewModel.startMov -> StartInputMovEquipVisitTerc -> java.lang.Exception",
            state.failure
        )
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.descrVigia,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.descrLocal,
            ""
        )
        assertEquals( // actual, expected (list) - Estado inicial/erro
            state.movEquipVisitTercModelList,
            emptyList<MovEquipVisitTercModel>()
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return true if startMov execute correctly`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            startInputMovEquipVisitTerc()
        ).thenReturn(
            Result.success(true)
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel()

        // Act
        viewModel.startMov()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagAccess,
            true // Acesso/sucesso ocorreu
        )
        assertEquals( // actual, expected (boolean) - Verificação de ausência de erro
            state.flagDialog,
            false
        )
        
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado inicial
            state.descrVigia,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial
            state.descrLocal,
            ""
        )
        assertEquals( // actual, expected (list) - Estado inicial
            state.movEquipVisitTercModelList,
            emptyList<MovEquipVisitTercModel>()
        )
    }
}
