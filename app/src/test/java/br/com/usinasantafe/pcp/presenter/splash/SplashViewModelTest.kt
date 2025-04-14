package br.com.usinasantafe.pcp.presenter.splash

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Já presente
import br.com.usinasantafe.pcp.domain.usecases.initial.AdjustConfig
import br.com.usinasantafe.pcp.domain.usecases.initial.CheckMovOpen
import br.com.usinasantafe.pcp.domain.usecases.initial.DeleteMovSent
import br.com.usinasantafe.pcp.utils.Errors // <<<--- IMPORT ADICIONADO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // PADRONIZADO: Mocks declarados como propriedades da classe
    private val adjustConfig = mock<AdjustConfig>()
    private val deleteMovSent = mock<DeleteMovSent>()
    private val checkMovOpen = mock<CheckMovOpen>()

    // PADRONIZADO: Helper function usa os mocks da classe
    private fun getViewModel() = SplashViewModel(
        adjustConfig = adjustConfig,
        deleteMovSent = deleteMovSent,
        checkMovOpen = checkMovOpen
    )

    @Test
    fun `Test new`() = runTest { // Renomeado para clareza: `Check initial state and setOpenDialog`
        val viewModel = getViewModel()
        var state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificando estado inicial (assumindo sem falha)
        assertEquals( // Padronizado
            state.flagDialog,
            false
        )
        

        viewModel.setOpenDialog()
        state = viewModel.uiState.value // Atualiza o state
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificando estado após setOpenDialog
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        
    }

    @Test
    fun `Check return failure if have error in adjustConfig`() = runTest {
        whenever(
            adjustConfig("1.00")
        ).thenReturn(
            // PADRONIZADO: Usando resultFailure
            resultFailure(
                "AdjustConfig",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.processInitial("1.00")
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de falha
        assertEquals( // Padronizado
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "SplashViewModel.processInitial -> AdjustConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have error in deleteMovSent`() = runTest {
        whenever(
            adjustConfig("1.00")
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            deleteMovSent()
        ).thenReturn(
            // PADRONIZADO: Usando resultFailure
            resultFailure(
                "DeleteMovSent",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.processInitial("1.00")
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de falha
        assertEquals( // Padronizado (substituído assertTrue)
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "SplashViewModel.processInitial -> DeleteMovSent -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have error in checkMovOpen`() = runTest {
        whenever(
            adjustConfig("1.00")
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            deleteMovSent()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            checkMovOpen()
        ).thenReturn(
            // PADRONIZADO: Usando resultFailure
            resultFailure(
                "CheckMovOpen",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.processInitial("1.00")
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de falha
        assertEquals( // Padronizado (substituído assertTrue)
            state.flagDialog,
            true
        )
        assertEquals( // Padronizado
            state.failure,
            "SplashViewModel.processInitial -> CheckMovOpen -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return false and check data if processInitial execute successfully`() = runTest {
        whenever(
            adjustConfig("1.00")
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            deleteMovSent()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            checkMovOpen()
        ).thenReturn(
            Result.success(false) // Movimento não aberto
        )
        val viewModel = getViewModel()
        viewModel.processInitial("1.00")
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de sucesso
        assertEquals( // Padronizado (substituído assertFalse)
            state.flagDialog,
            false
        )
        assertEquals( // Padronizado (substituído assertTrue)
            state.flagAccess,
            true
        )
        assertEquals( // Padronizado (substituído assertFalse)
            state.flagMovOpen,
            false // Resultado esperado do checkMovOpen
        )
        
    }

    @Test
    fun `Check return true and check data if processInitial execute successfully`() = runTest {
        whenever(
            adjustConfig("1.00")
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            deleteMovSent()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            checkMovOpen()
        ).thenReturn(
            Result.success(true) // Movimento aberto
        )
        val viewModel = getViewModel()
        viewModel.processInitial("1.00")
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas
        // PADRONIZADO: Verificações completas de sucesso
        assertEquals( // Padronizado (substituído assertFalse)
            state.flagDialog,
            false
        )
        assertEquals( // Padronizado (substituído assertTrue)
            state.flagAccess,
            true
        )
        assertEquals( // Padronizado (substituído assertTrue)
            state.flagMovOpen,
            true // Resultado esperado do checkMovOpen
        )
        
    }
}
