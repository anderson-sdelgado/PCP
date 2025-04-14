package br.com.usinasantafe.pcp.presenter.residencia.detalhe

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.residencia.CloseMovResidencia
import br.com.usinasantafe.pcp.domain.usecases.residencia.GetDetalheResidencia
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Adicionado/Confirmado como a importação correta
import br.com.usinasantafe.pcp.utils.Errors // <<<--- IMPORT ADICIONADO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetalheResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Mocks movidos para fora das funções de teste
    private val getDetalheResidencia = mock<GetDetalheResidencia>()
    private val closeMovResidencia = mock<CloseMovResidencia>()

    // Função helper para criar o ViewModel
    private fun createViewModel(savedStateHandle: SavedStateHandle) = DetalheResidenciaViewModel(
        savedStateHandle,
        getDetalheResidencia,
        closeMovResidencia
    )

    @Test
    fun `Check return failure if have error in recoverDetalhe`() = runTest {
        whenever(
            getDetalheResidencia(1)
        ).thenReturn(
            resultFailure(
                "GetDetalheResidencia",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            )
        )
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "DetalheResidenciaViewModel.recoverDetalhe -> GetDetalheResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return model if recoverDetalhe execute correctly`() = runTest {
        val detalheModel = DetalheResidenciaModel(
            id = 1,
            dthr = "08/08/2024 12:00",
            tipoMov = "ENTRADA",
            veiculo = "GOL",
            placa = "AAA-0000",
            motorista = "19759 - ANDERSON DA SILVA DELGADO",
            observ = "Teste Observ",
        )
        whenever(getDetalheResidencia(1)).thenReturn(
            Result.success(detalheModel)
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                ),
            )
        )
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            false // Garante que não houve diálogo de erro
        )
        assertEquals( // Padronizado
            state.dthr,
            "08/08/2024 12:00"
        )
        assertEquals( // Padronizado
            state.tipoMov,
            "ENTRADA"
        )
        assertEquals( // Padronizado
            state.veiculo,
            "GOL"
        )
        assertEquals( // Padronizado
            state.placa,
            "AAA-0000"
        )
        assertEquals( // Padronizado
            state.motorista,
            "19759 - ANDERSON DA SILVA DELGADO"
        )
        assertEquals( // Padronizado
            state.observ,
            "Teste Observ"
        )
        
    }

    @Test
    fun `Check return failure if have error in CloseMovResidencia`() = runTest { // Nome do teste ligeiramente ajustado
        whenever(
            closeMovResidencia(1)
        ).thenReturn(
            resultFailure(
                "CloseMovResidencia",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                ),
            )
        )
        viewModel.closeMov()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "DetalheResidenciaViewModel.closeMov -> CloseMovResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if CloseMovResidencia execute correctly`() = runTest { // Nome do teste ligeiramente ajustado
        whenever(closeMovResidencia(1)).thenReturn(
            Result.success(true)
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                ),
            )
        )
        viewModel.closeMov()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            false // Garante que não houve diálogo de erro
        )
        assertEquals( // Padronizado
            state.flagCloseMov,
            true
        )
        
    }
}
