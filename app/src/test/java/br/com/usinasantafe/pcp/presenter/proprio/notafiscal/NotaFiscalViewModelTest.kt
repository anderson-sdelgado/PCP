package br.com.usinasantafe.pcp.presenter.proprio.notafiscal

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetNotaFiscalProprio
import br.com.usinasantafe.pcp.domain.usecases.proprio.SetNotaFiscalProprio
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class NotaFiscalViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return access true if button ok pressed`() = runTest {
        val setNotaFiscalProprio = mock<SetNotaFiscalProprio>()
        val getNotaFiscalProprio = mock<GetNotaFiscalProprio>()
        val viewModel = NotaFiscalViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setNotaFiscalProprio,
            getNotaFiscalProprio
        )
        viewModel.setTextField("OK", TypeButton.OK)
        assertTrue(viewModel.uiState.value.flagAccess)
    }

    @Test
    fun `Check return failure if have error in SetNotaFiscalProprio`() = runTest {
        val setNotaFiscalProprio = mock<SetNotaFiscalProprio>()
        val getNotaFiscalProprio = mock<GetNotaFiscalProprio>()
        whenever(
            setNotaFiscalProprio(
                notaFiscal = "123456",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = NotaFiscalViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setNotaFiscalProprio,
            getNotaFiscalProprio
        )
        viewModel.setTextField("123456", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> SetNotaFiscalProprio -> java.lang.Exception")
    }

    @Test
    fun `Check return access true if SetNotaFiscalProprio execute successfully`() = runTest {
        val setNotaFiscalProprio = mock<SetNotaFiscalProprio>()
        val getNotaFiscalProprio = mock<GetNotaFiscalProprio>()
        whenever(
            setNotaFiscalProprio(
                notaFiscal = "123456",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = NotaFiscalViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setNotaFiscalProprio,
            getNotaFiscalProprio
        )
        viewModel.setTextField("123456", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertFalse(viewModel.uiState.value.flagDialog)
        assertTrue(viewModel.uiState.value.flagAccess)
    }

    @Test
    fun `Check return failure if have error in GetNotaFiscalProprio`() = runTest {
        val setNotaFiscalProprio = mock<SetNotaFiscalProprio>()
        val getNotaFiscalProprio = mock<GetNotaFiscalProprio>()
        whenever(
            getNotaFiscalProprio(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = NotaFiscalViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1,
                )
            ),
            setNotaFiscalProprio,
            getNotaFiscalProprio
        )
        viewModel.getNotaFiscal()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> GetNotaFiscalProprio -> java.lang.Exception")
    }


    @Test
    fun `Check return observ if GetNotaFiscalProprio execute successfully`() = runTest {
        val setNotaFiscalProprio = mock<SetNotaFiscalProprio>()
        val getNotaFiscalProprio = mock<GetNotaFiscalProprio>()
        whenever(
            getNotaFiscalProprio(
                id = 1
            )
        ).thenReturn(
            Result.success("123456")
        )
        val viewModel = NotaFiscalViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1,
                )
            ),
            setNotaFiscalProprio,
            getNotaFiscalProprio
        )
        viewModel.getNotaFiscal()
        assertFalse(viewModel.uiState.value.flagDialog)
        assertEquals(viewModel.uiState.value.notaFiscal, "123456")
    }
}