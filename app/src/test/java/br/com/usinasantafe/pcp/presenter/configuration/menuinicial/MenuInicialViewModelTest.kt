package br.com.usinasantafe.pcp.presenter.configuration.menuinicial

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
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


    private val checkAccessMain = mock<CheckAccessMain>()
    private val getStatusSend = mock<GetStatusSend>()
    private val viewModel = MenuInicialViewModel(
        checkAccessMain,
        getStatusSend
    )

    @Test
    fun `check return failure if checkAccess have failure`() = runTest {
        whenever(
            checkAccessMain()
        ).thenReturn(
            resultFailure(
                "CheckAccessMain",
                "-",
                Exception()
            )
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
            "MenuInicialViewModel.checkAccess -> CheckAccessMain -> java.lang.Exception"
        )
    }

    @Test
    fun `check blocked access`() = runTest {
        whenever(
            checkAccessMain()
        ).thenReturn(
            Result.success(false)
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
        whenever(
            checkAccessMain()
        ).thenReturn(
            Result.success(true)
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