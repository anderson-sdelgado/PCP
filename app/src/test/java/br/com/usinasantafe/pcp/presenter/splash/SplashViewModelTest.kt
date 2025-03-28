package br.com.usinasantafe.pcp.presenter.splash

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.initial.AdjustConfig
import br.com.usinasantafe.pcp.domain.usecases.initial.CheckMovOpen
import br.com.usinasantafe.pcp.domain.usecases.initial.DeleteMovSent
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

    private val adjustConfig = mock<AdjustConfig>()
    private val deleteMovSent = mock<DeleteMovSent>()
    private val checkMovOpen = mock<CheckMovOpen>()

    private fun getViewModel() = SplashViewModel(
        adjustConfig = adjustConfig,
        deleteMovSent = deleteMovSent,
        checkMovOpen = checkMovOpen
    )

    @Test
    fun `Test new`() = runTest {
        val viewModel = getViewModel()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            false
        )
        viewModel.setOpenDialog()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
    }

    @Test
    fun `Check return failure if have error in adjustConfig`() = runTest {
        whenever(
            adjustConfig("1.00")
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.processInitial("1.00")
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> AdjustConfig -> java.lang.Exception"
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
            Result.failure(
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.processInitial("1.00")
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> DeleteMovSent -> java.lang.Exception"
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
            Result.failure(
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.processInitial("1.00")
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> CheckMovOpen -> java.lang.Exception"
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
            Result.success(false)
        )
        val viewModel = getViewModel()
        viewModel.processInitial("1.00")
        assertFalse(viewModel.uiState.value.flagDialog)
        assertTrue(viewModel.uiState.value.flagAccess)
        assertFalse(viewModel.uiState.value.flagMovOpen)
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
            Result.success(true)
        )
        val viewModel = getViewModel()
        viewModel.processInitial("1.00")
        assertFalse(viewModel.uiState.value.flagDialog)
        assertTrue(viewModel.uiState.value.flagAccess)
        assertTrue(viewModel.uiState.value.flagMovOpen)
    }
}