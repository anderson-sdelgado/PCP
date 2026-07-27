package br.com.usinasantafe.pcp.presenter.residencia.placa

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Já presente
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetPlacaResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.SetPlacaResidencia
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.lib.FlowApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PlacaResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // PADRONIZADO: Mocks declarados como propriedades da classe
    private val getPlacaResidencia = mock<GetPlacaResidencia>()
    private val setPlacaResidencia = mock<SetPlacaResidencia>()

    // PADRONIZADO: Helper function usa os mocks da classe
    private fun createViewModel(
        savedStateHandle: SavedStateHandle
    ) = PlacaResidenciaViewModel(
        savedStateHandle,
        getPlacaResidencia, // Usa o mock da classe
        setPlacaResidencia  // Usa o mock da classe
    )

    @Test
    fun `Check return failure if fields is empty`() {
        // PADRONIZADO: Chamada simplificada para createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setPlaca()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações de falha (adaptado para validação interna)
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
    }

    @Test
    fun `Check return failure if have error in setPlaca`() = runTest {
        whenever(
            setPlacaResidencia(
                placa = "AAA0000",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            // PADRONIZADO: Usando resultFailure
            resultFailure(
                "SetPlacaResidencia",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Chamada simplificada para createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.onPlacaChanged("AAA0000")
        viewModel.setPlaca()
        val state = viewModel.uiState.value
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "PlacaResidenciaViewModel.setPlaca -> SetPlacaResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check access true if setPlaca execute successfully`() = runTest { // Nome ajustado para clareza
        whenever(
            setPlacaResidencia(
                placa = "AAA0000",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        // PADRONIZADO: Chamada simplificada para createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.onPlacaChanged("AAA0000")
        viewModel.setPlaca()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de sucesso
        assertEquals( // Padronizado
            state.flagAccess,
            true
        )
        assertEquals( // Padronizado
            state.flagDialog,
            false
        )
        
    }

    @Test
    fun `Check return failure if have error in GetPlaca`() = runTest {
        whenever(
            getPlacaResidencia(
                id = 1
            )
        ).thenReturn(
            // PADRONIZADO: Usando resultFailure
            resultFailure(
                "GetPlacaResidencia",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Chamada simplificada para createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )
        viewModel.recoverPlaca()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de falha
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "PlacaResidenciaViewModel.recoverPlaca -> GetPlacaResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return placa if GetPlaca execute successfully`() = runTest {
        whenever(
            getPlacaResidencia(
                id = 1
            )
        ).thenReturn(
            Result.success("AAA-0000")
        )
        // PADRONIZADO: Chamada simplificada para createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )
        viewModel.recoverPlaca()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de sucesso (adaptado para recuperação de dados)
        assertEquals( // Padronizado
            state.placa,
            "AAA-0000"
        )
        assertEquals( // Padronizado
            state.flagDialog,
            false
        )
        
    }
}
