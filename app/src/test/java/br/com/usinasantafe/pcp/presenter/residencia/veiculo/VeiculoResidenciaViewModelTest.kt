package br.com.usinasantafe.pcp.presenter.residencia.veiculo

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Já presente
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetVeiculoResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.SetVeiculoResidencia
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
class VeiculoResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // PADRONIZADO: Mocks declarados como propriedades da classe
    private val getVeiculoResidencia = mock<GetVeiculoResidencia>()
    private val setVeiculoResidencia = mock<SetVeiculoResidencia>()

    // PADRONIZADO: Helper function usa os mocks da classe
    private fun createViewModel(
        savedStateHandle: SavedStateHandle
    ) = VeiculoResidenciaViewModel(
        savedStateHandle,
        getVeiculoResidencia, // Usa o mock da classe
        setVeiculoResidencia  // Usa o mock da classe
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
        viewModel.setVeiculo()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações de falha (adaptado para validação interna)
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
    }

    @Test
    fun `Check return failure if have error in setVeiculo`() = runTest {
        whenever(
            setVeiculoResidencia(
                veiculo = "GOL",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            // PADRONIZADO: Usando resultFailure
            resultFailure(
                "SetVeiculoResidencia",
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
        viewModel.onVeiculoChanged("GOL")
        viewModel.setVeiculo()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de falha
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "VeiculoResidenciaViewModel.setVeiculo -> SetVeiculoResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check access true if setVeiculo execute successfully`() = runTest { // Nome ajustado para clareza
        whenever(
            setVeiculoResidencia(
                veiculo = "GOL",
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
        viewModel.onVeiculoChanged("GOL")
        viewModel.setVeiculo()
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
    fun `Check return failure if have error in GetVeiculo`() = runTest {
        whenever(
            getVeiculoResidencia(
                id = 1
            )
        ).thenReturn(
            // PADRONIZADO: Usando resultFailure
            resultFailure(
                "GetVeiculoResidencia",
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
        viewModel.recoverVeiculo()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de falha
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "VeiculoResidenciaViewModel.recoverVeiculo -> GetVeiculoResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return veiculo if GetVeiculo execute successfully`() = runTest {
        whenever(
            getVeiculoResidencia(
                id = 1
            )
        ).thenReturn(
            Result.success("GOL")
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
        viewModel.recoverVeiculo()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de sucesso (adaptado para recuperação de dados)
        assertEquals( // Padronizado
            state.veiculo,
            "GOL"
        )
        assertEquals( // Padronizado
            state.flagDialog,
            false
        )
        
    }
}
