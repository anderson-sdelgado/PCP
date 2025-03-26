package br.com.usinasantafe.pcp.presenter.configuration.menuinicial

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.common.GetStatusSend
import br.com.usinasantafe.pcp.domain.usecases.config.CheckAccessMain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MenuInicialViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `check return failure if checkAccess have failure`() = runTest {
        val checkAccessMain = mock<CheckAccessMain>()
        val getStatusSend = mock<GetStatusSend>()
        whenever(
            checkAccessMain()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = MenuInicialViewModel(
            checkAccessMain,
            getStatusSend
        )
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
            "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `check blocked access`() = runTest {
        val checkAccessMain = mock<CheckAccessMain>()
        val getStatusSend = mock<GetStatusSend>()
        whenever(
            checkAccessMain()
        ).thenReturn(
            Result.success(false)
        )
        val viewModel = MenuInicialViewModel(
            checkAccessMain,
            getStatusSend
        )
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
        val checkAccessMain = mock<CheckAccessMain>()
        val getStatusSend = mock<GetStatusSend>()
        whenever(
            checkAccessMain()
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = MenuInicialViewModel(
            checkAccessMain,
            getStatusSend
        )
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