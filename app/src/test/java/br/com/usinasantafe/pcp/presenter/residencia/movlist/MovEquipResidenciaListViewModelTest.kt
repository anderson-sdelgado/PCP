package br.com.usinasantafe.pcp.presenter.residencia.movlist

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.residencia.GetMovEquipResidenciaInsideList
import br.com.usinasantafe.pcp.domain.usecases.residencia.StartInputMovEquipResidencia
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import br.com.usinasantafe.pcp.presenter.residencia.model.MovEquipResidenciaModel
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Adicionado/Confirmado
import br.com.usinasantafe.pcp.utils.Errors // <<<--- IMPORT ADICIONADO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovEquipResidenciaListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Mocks movidos para fora das funções de teste
    private val getHeader = mock<GetHeader>()
    private val getMovEquipResidenciaInsideList = mock<GetMovEquipResidenciaInsideList>()
    private val startInputMovEquipResidencia = mock<StartInputMovEquipResidencia>() // Nome corrigido

    // Função helper para criar o ViewModel
    private fun createViewModel() = MovEquipResidenciaListViewModel(
        getHeader,
        getMovEquipResidenciaInsideList,
        startInputMovEquipResidencia // Usando nome corrigido
    )

    @Test
    fun `Check return failure if have error in getHeader`() = runTest {
        whenever(
            getHeader()
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "GetHeader", // Nome do Use Case que falhou
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel()
        viewModel.returnHeader()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "MovEquipResidenciaListViewModel.returnHeader -> GetHeader -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return Nome Vigia if getHeader execute correctly`() = runTest {
        whenever(
            getHeader()
        ).thenReturn(
            Result.success(
                HeaderModel(
                    descrVigia = "19759 - Anderson da Silva Delgado",
                    descrLocal = "1 - Usina"
                )
            )
        )
        val viewModel = createViewModel()
        viewModel.returnHeader()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            false // Garante que não houve diálogo de erro
        )
        assertEquals( // Padronizado
            state.descrVigia,
            "19759 - Anderson da Silva Delgado"
        )
        assertEquals( // Padronizado
            state.descrLocal,
            "1 - Usina"
        )
        
    }

    @Test
    fun `Check return failure if have error in recoverMovEquipInputList`() = runTest {
        whenever(
            getMovEquipResidenciaInsideList()
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "GetMovEquipResidenciaInsideList", // Nome do Use Case que falhou
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel()
        viewModel.recoverMovEquipList()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "MovEquipResidenciaListViewModel.recoverMovEquipList -> GetMovEquipResidenciaInsideList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list if recoverMovEquipInputList execute correctly`() = runTest {
        val list = listOf(
            MovEquipResidenciaModel(
                id = 1,
                dthr = "DATA/HORA: 08/08/2024 12:00",
                motorista = "MOTORISTA: ANDERSON DA SILVA DELGADO",
                veiculo = "VEÍCULO: GOL",
                placa = "PLACA: ABC1234",
            )
        )
        whenever(getMovEquipResidenciaInsideList()).thenReturn(
            Result.success(list)
        )
        val viewModel = createViewModel()
        viewModel.recoverMovEquipList()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            false // Garante que não houve diálogo de erro
        )
        assertEquals( // Padronizado
            state.movEquipResidenciaModelList.size,
            1
        )
        assertEquals( // Padronizado
            state.movEquipResidenciaModelList[0].dthr,
            "DATA/HORA: 08/08/2024 12:00"
        )
        assertEquals( // Padronizado
            state.movEquipResidenciaModelList,
            list // Compara a lista inteira
        )
        
    }

    @Test
    fun `Check return failure if have error in startMovEquipResidencia`() = runTest {
        whenever(
            startInputMovEquipResidencia() // Usando nome corrigido
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "StartInputMovEquipResidencia", // Nome do Use Case que falhou
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel()
        viewModel.startMov()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "MovEquipResidenciaListViewModel.startMov -> StartInputMovEquipResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if startMovEquipResidencia execute correctly`() = runTest {
        whenever(
            startInputMovEquipResidencia() // Usando nome corrigido
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = createViewModel()
        viewModel.startMov()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagAccess,
            true
        )
        assertEquals( // Padronizado
            state.flagDialog,
            false // Garante que não houve diálogo de erro
        )
        
    }
}
