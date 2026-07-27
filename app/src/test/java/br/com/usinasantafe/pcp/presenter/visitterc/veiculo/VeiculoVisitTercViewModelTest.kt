package br.com.usinasantafe.pcp.presenter.visitterc.veiculo

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetVeiculoVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.SetVeiculoVisitTerc
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
class VeiculoVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Mocks como propriedades da classe
    private val setVeiculoVisitTerc = mock<SetVeiculoVisitTerc>()
    private val getVeiculoVisitTerc = mock<GetVeiculoVisitTerc>()

    // Helper function para criar ViewModel
    private fun getViewModel(
        savedStateHandle: SavedStateHandle
    ) = VeiculoVisitTercViewModel(
        savedStateHandle,
        setVeiculoVisitTerc,
        getVeiculoVisitTerc
    )

    @Test
    fun `Check return failure if field is empty`() { // Não é runTest pois não há suspend calls
        // Arrange
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        // Act
        viewModel.setVeiculo() // Tenta salvar com veículo vazio (estado inicial)
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.veiculo,
            ""
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.checkGetVeiculo,
            true
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return failure if have error in setVeiculo`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            setVeiculoVisitTerc(
                veiculo = "GOL",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "SetVeiculoVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        // Act
        viewModel.onVeiculoChanged("GOL")
        viewModel.setVeiculo()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem padronizado
            "VeiculoVisitTercViewModel.setVeiculo -> SetVeiculoVisitTerc -> java.lang.Exception",
            state.failure
        )
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado atualizado, mas falhou
            state.veiculo,
            "GOL"
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.checkGetVeiculo,
            true
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return true if setVeiculo execute successfully`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            setVeiculoVisitTerc(
                veiculo = "GOL",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        // Act
        viewModel.onVeiculoChanged("GOL")
        viewModel.setVeiculo()
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
        assertEquals( // actual, expected (string) - Estado atualizado e salvo
            state.veiculo,
            "GOL"
        )
        assertEquals( // actual, expected (boolean) - Estado inicial
            state.checkGetVeiculo,
            true
        )
    }

    @Test
    fun `Check return failure if have error in GetVeiculo`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            getVeiculoVisitTerc(
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "GetVeiculoVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.recoverVeiculo()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem padronizado
            "VeiculoVisitTercViewModel.recoverVeiculo -> GetVeiculoVisitTerc -> java.lang.Exception",
            state.failure
        )
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.veiculo,
            ""
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.checkGetVeiculo,
            true
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return veiculo if GetVeiculo execute successfully`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            getVeiculoVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("GOL")
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.recoverVeiculo()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // expected, actual (string)
            "GOL",
            state.veiculo
        )
        assertEquals( // actual, expected (boolean)
            state.checkGetVeiculo,
            false // Recuperou com sucesso
        )
        assertEquals( // actual, expected (boolean) - Verificação de ausência de erro
            state.flagDialog,
            false
        )
        
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (boolean) - Estado inicial
            state.flagAccess,
            false
        )
    }
}
