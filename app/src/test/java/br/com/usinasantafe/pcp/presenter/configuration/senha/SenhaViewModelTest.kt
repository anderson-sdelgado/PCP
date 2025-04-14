package br.com.usinasantafe.pcp.presenter.configuration.senha

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.config.CheckPassword
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SenhaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    val password = "12345"

    val checkPassword = mock<CheckPassword>()
    val viewModel = SenhaViewModel(checkPassword)

    @Test
    fun `check return failure if checkPassword have failure`() = runTest {
        whenever(
            checkPassword(password)
        ).thenReturn(
            resultFailure(
                "CheckPassword",
                "-",
                Exception()
            )
        )
        viewModel.updatePassword(password)
        viewModel.checkAccess()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            true
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            false
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "SenhaViewModel.checkAccess -> CheckPassword -> java.lang.Exception"
        )
    }

    @Test
    fun `check blocked access`() = runTest {
        whenever(checkPassword(password)).thenReturn(
            Result.success(false)
        )
        viewModel.updatePassword(password)
        viewModel.checkAccess()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            false
        )
    }

    @Test
    fun `check access granted`() = runTest {
        whenever(checkPassword(password)).thenReturn(
            Result.success(true)
        )
        viewModel.updatePassword(password)
        viewModel.checkAccess()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            true
        )
    }

}