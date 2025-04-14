package br.com.usinasantafe.pcp.presenter.visitterc.observ

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.visitterc.GetObservVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.SaveMovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.SetObservVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.StartOutputMovEquipVisitTerc
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ObservVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Mocks como propriedades da classe
    private val setObservVisitTerc = mock<SetObservVisitTerc>()
    private val getObservVisitTerc = mock<GetObservVisitTerc>()
    private val startOutputMovEquipVisitTerc = mock<StartOutputMovEquipVisitTerc>()
    private val saveMovEquipVisitTerc = mock<SaveMovEquipVisitTerc>()

    // Helper function para criar ViewModel
    private fun getViewModel(savedStateHandle: SavedStateHandle) =
        ObservVisitTercViewModel(
            savedStateHandle,
            setObservVisitTerc,
            getObservVisitTerc,
            startOutputMovEquipVisitTerc,
            saveMovEquipVisitTerc
        )

    @Test
    fun `Check return failure if have error in GetObserv`() = runTest {
        whenever(
            getObservVisitTerc(
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "GetObservVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
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

        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            "ObservVisitTercViewModel.recoverObserv -> GetObservVisitTerc -> java.lang.Exception",
            state.failure
        )
        assertEquals(
            state.observ,
            null
        )
        assertEquals(
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return observ if GetObserv execute successfully`() = runTest {
        whenever(
            getObservVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        val viewModel = getViewModel(
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

        assertEquals(
            "Observação",
            state.observ
        )
        assertEquals(
            state.flagDialog,
            false
        )

        assertEquals(
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return failure if have error in StartOutputMovEquipVisitTerc`() = runTest {
        whenever(
            startOutputMovEquipVisitTerc(
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "StartOutputMovEquipVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.OUTPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value

        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            "ObservVisitTercViewModel.setObserv -> StartOutputMovEquipVisitTerc -> java.lang.Exception",
            state.failure
        )
        assertEquals(
            state.observ,
            "Observação"
        )
        assertEquals(
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return failure if have error in SetObserv`() = runTest {
        whenever(
            startOutputMovEquipVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success(true) // Simula sucesso no primeiro use case
        )
        // PADRONIZADO: whenever multi-linha (falha no segundo)
        whenever(
            setObservVisitTerc(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "SetObservVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.OUTPUT.ordinal, // Mantido OUTPUT para testar a lógica condicional
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem padronizado
            "ObservVisitTercViewModel.setObserv -> SetObservVisitTerc -> java.lang.Exception",
            state.failure
        )
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado atualizado, mas falhou
            state.observ,
            "Observação"
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return failure if have error in SaveMovEquipVisitTerc`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha (sucesso no SetObservVisitTerc)
        whenever(
            setObservVisitTerc(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        // PADRONIZADO: whenever multi-linha (falha no SaveMovEquipVisitTerc)
        whenever(
            saveMovEquipVisitTerc(
                typeMov = TypeMovEquip.INPUT,
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "SaveMovEquipVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal, // Mantido INPUT para testar a lógica condicional
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem padronizado
            "ObservVisitTercViewModel.setObserv -> SaveMovEquipVisitTerc -> java.lang.Exception",
            state.failure
        )
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado atualizado, mas falhou
            state.observ,
            "Observação"
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return true if SetObserv execute successfully for INPUT`() = runTest { // Nome mais específico
        // Arrange
        // PADRONIZADO: whenever multi-linha (sucesso no SetObservVisitTerc)
        whenever(
            setObservVisitTerc(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        // PADRONIZADO: whenever multi-linha (sucesso no SaveMovEquipVisitTerc)
        whenever(
            saveMovEquipVisitTerc(
                typeMov = TypeMovEquip.INPUT,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
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
            state.observ,
            "Observação"
        )
    }

    @Test
    fun `Check return true if SetObserv execute successfully for OUTPUT`() = runTest { // Teste adicional para OUTPUT
        // Arrange
        // PADRONIZADO: whenever multi-linha (sucesso no StartOutputMovEquipVisitTerc)
        whenever(
            startOutputMovEquipVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        // PADRONIZADO: whenever multi-linha (sucesso no SetObservVisitTerc)
        whenever(
            setObservVisitTerc(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.OUTPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
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
            state.observ,
            "Observação"
        )
    }

}
