package br.com.usinasantafe.pcp.presenter.configuration.senha

import br.com.usinasantafe.pcp.MainCoroutineRule
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

    private fun getViewModel() = SenhaViewModel(
        checkPassword = mock()
    )

    @Test
    fun `check return failure if checkPassword have failure`() = runTest {
        val checkPassword = mock<CheckPassword>()
        whenever(
            checkPassword(password)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = SenhaViewModel(checkPassword)
        viewModel.updatePassword(password)
        viewModel.checkAccess()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.flagFailure, true)
        assertEquals(viewModel.uiState.value.flagAccess, false)
        assertEquals(viewModel.uiState.value.failure, "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception")
    }

    @Test
    fun `check blocked access`() = runTest {
        val checkPassword = mock<CheckPassword>()
        whenever(checkPassword(password)).thenReturn(
            Result.success(false)
        )
        val viewModel = SenhaViewModel(checkPassword)
        viewModel.updatePassword(password)
        viewModel.checkAccess()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.flagFailure, false)
        assertEquals(viewModel.uiState.value.flagAccess, false)
    }

    @Test
    fun `check access granted`() = runTest {
        val checkPassword = mock<CheckPassword>()
        whenever(checkPassword(password)).thenReturn(
            Result.success(true)
        )
        val viewModel = SenhaViewModel(checkPassword)
        viewModel.updatePassword(password)
        viewModel.checkAccess()
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.flagFailure, false)
        assertEquals(viewModel.uiState.value.flagAccess, true)
    }

}