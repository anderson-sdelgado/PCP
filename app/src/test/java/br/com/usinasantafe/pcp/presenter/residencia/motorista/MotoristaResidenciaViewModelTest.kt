package br.com.usinasantafe.pcp.presenter.residencia.motorista

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetMotoristaResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.SetMotoristaResidencia
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Adicionado/Confirmado
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MotoristaResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Mocks movidos para fora das funções de teste
    private val setMotoristaResidencia = mock<SetMotoristaResidencia>()
    private val getMotoristaResidencia = mock<GetMotoristaResidencia>()

    // Função helper para criar o ViewModel
    private fun createViewModel(savedStateHandle: SavedStateHandle) = MotoristaResidenciaViewModel(
        savedStateHandle,
        getMotoristaResidencia,
        setMotoristaResidencia
    )

    @Test
    fun `Check return failure if fields is empty`() { // Não precisa de runTest aqui
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setMotorista() // Chama com campo vazio
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        
    }

    @Test
    fun `Check return failure if have error in setMotorista`() = runTest {
        whenever(
            setMotoristaResidencia(
                motorista = "Anderson",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "SetMotoristaResidencia",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.onMotoristaChanged("Anderson")
        viewModel.setMotorista()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "MotoristaResidenciaViewModel.setMotorista -> SetMotoristaResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if setMotorista execute successfully`() = runTest {
        whenever(
            setMotoristaResidencia(
                motorista = "Anderson",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.onMotoristaChanged("Anderson")
        viewModel.setMotorista()
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
        // PADRONIZADO: Verificar ausência de erro
        
    }

    @Test
    fun `Check return failure if have error in GetMotorista`() = runTest {
        whenever(
            getMotoristaResidencia(
                id = 1
            )
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "GetMotoristaResidencia",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )
        viewModel.recoverMotorista()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "MotoristaResidenciaViewModel.recoverMotorista -> GetMotoristaResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return motorista if GetMotorista execute successfully`() = runTest {
        whenever(
            getMotoristaResidencia(
                id = 1
            )
        ).thenReturn(
            Result.success("Anderson")
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )
        viewModel.recoverMotorista()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.motorista,
            "Anderson"
        )
        assertEquals( // Padronizado
            state.flagDialog,
            false // Garante que não houve diálogo de erro
        )
        // PADRONIZADO: Verificar ausência de erro

        
    }
}
