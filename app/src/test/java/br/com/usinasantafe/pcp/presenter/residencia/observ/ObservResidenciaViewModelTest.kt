package br.com.usinasantafe.pcp.presenter.residencia.observ

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.residencia.GetObservResidencia
import br.com.usinasantafe.pcp.domain.usecases.residencia.SaveMovEquipResidencia
import br.com.usinasantafe.pcp.domain.usecases.residencia.SetObservResidencia
import br.com.usinasantafe.pcp.domain.usecases.residencia.StartOutputMovEquipResidencia
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Adicionado/Confirmado
import br.com.usinasantafe.pcp.utils.Errors // <<<--- IMPORT ADICIONADO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ObservResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // PADRONIZADO: Mocks como propriedades da classe
    private val setObservResidencia = mock<SetObservResidencia>()
    private val getObservResidencia = mock<GetObservResidencia>()
    private val startOutputMovEquipResidencia = mock<StartOutputMovEquipResidencia>()
    private val saveMovEquipResidencia = mock<SaveMovEquipResidencia>()

    // PADRONIZADO: Helper function para criar ViewModel
    private fun createViewModel(savedStateHandle: SavedStateHandle) =
        ObservResidenciaViewModel(
            savedStateHandle,
            setObservResidencia,
            getObservResidencia,
            startOutputMovEquipResidencia,
            saveMovEquipResidencia
        )

    @Test
    fun `Check return failure if have error in GetObserv`() = runTest {
        whenever(
            getObservResidencia(
                id = 1
            )
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "GetObservResidencia",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.recoverObserv()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "ObservResidenciaViewModel.recoverObserv -> GetObservResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return observ if GetObserv execute successfully`() = runTest {
        whenever(
            getObservResidencia(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        // PADRONIZADO: Usando createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.recoverObserv()
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            false // Garante que não houve diálogo de erro
        )
        assertEquals( // Padronizado
            state.observ,
            "Observação"
        )
        
    }

    @Test
    fun `Check return failure if have error in StartOutputMovEquipResidencia`() = runTest {
        whenever(
            startOutputMovEquipResidencia(
                id = 1
            )
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "StartOutputMovEquipResidencia",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.OUTPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv() // Método do ViewModel que dispara a lógica
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "ObservResidenciaViewModel.setObserv -> StartOutputMovEquipResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have error in SetObserv`() = runTest {
        // Mock para StartOutputMovEquipResidencia ter sucesso (necessário para chegar no SetObserv)
        // Adicionado para testar o caminho INPUT que vai direto para SetObserv
        whenever(
            startOutputMovEquipResidencia(
                id = 0 // Usando ID 0 para o caso INPUT
            )
        ).thenReturn(
            Result.success(true) // Simula sucesso, embora não seja chamado no fluxo INPUT
        )
        whenever(
            setObservResidencia(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 0 // Usando ID 0 para o caso INPUT
            )
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "SetObservResidencia",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal, // Testando o fluxo INPUT
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 0 // Usando ID 0 para o caso INPUT
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv() // Método do ViewModel que dispara a lógica
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "ObservResidenciaViewModel.setObserv -> SetObservResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have error in SaveMovEquipResidencia`() = runTest {
        // Mock para SetObservResidencia ter sucesso (necessário para chegar no Save)
        whenever(
            setObservResidencia(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        // Mock para SaveMovEquipResidencia falhar
        whenever(
            saveMovEquipResidencia(
                typeMov = TypeMovEquip.INPUT,
                id = 1
            )
        ).thenReturn(
            // PADRONIZADO - Simulação de Falha
            resultFailure(
                "SaveMovEquipResidencia",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv() // Método do ViewModel que dispara a lógica
        val state = viewModel.uiState.value
        // FORMATADO: Todos assertEquals em multi-linhas
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "ObservResidenciaViewModel.setObserv -> SaveMovEquipResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check access true if SetObserv execute successfully for INPUT`() = runTest { // Nome mais específico
        whenever(
            setObservResidencia(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            saveMovEquipResidencia(
                typeMov = TypeMovEquip.INPUT,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        // PADRONIZADO: Usando createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
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

    @Test
    fun `Check access true if SetObserv execute successfully for OUTPUT`() = runTest { // Teste adicional para OUTPUT
        whenever(
            startOutputMovEquipResidencia(
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            setObservResidencia(
                observ = "Observação",
                flowApp = FlowApp.ADD, // FlowApp é ADD para OUTPUT também
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        // PADRONIZADO: Usando createViewModel
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.OUTPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal, // FlowApp é ADD para OUTPUT também
                    ID_ARGS to 1
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
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
