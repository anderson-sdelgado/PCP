package br.com.usinasantafe.pcp.presenter.visitterc.placa

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetPlacaVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.SetPlacaVisitTerc
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.lib.FlowApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PlacaVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setPlacaVisitTerc = mock<SetPlacaVisitTerc>()
    private val getPlacaVisitTerc = mock<GetPlacaVisitTerc>()

    private fun getViewModel(
        savedStateHandle: SavedStateHandle
    ) = PlacaVisitTercViewModel(
        savedStateHandle,
        setPlacaVisitTerc,
        getPlacaVisitTerc
    )

    @Test
    fun `Check return failure if field is empty`() {
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        viewModel.setPlaca()
        val state = viewModel.uiState.value

        assertEquals(
            state.flagDialog,
            true
        )

        assertEquals(
            state.placa,
            ""
        )
        assertEquals(
            state.checkGetPlaca,
            true
        )
        assertEquals(
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return failure if have error in SetPlaca`() = runTest {
        whenever(
            setPlacaVisitTerc(
                placa = "AAA0000",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "SetPlacaVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        viewModel.onPlacaChanged("AAA0000")
        viewModel.setPlaca()
        val state = viewModel.uiState.value

        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            "PlacaVisitTercViewModel.setPlaca -> SetPlacaVisitTerc -> java.lang.Exception",
            state.failure
        )
        assertEquals(
            state.placa,
            "AAA0000"
        )
        assertEquals(
            state.checkGetPlaca,
            true
        )
        assertEquals(
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return true if SetPlaca execute success`() = runTest {
        whenever(
            setPlacaVisitTerc(
                placa = "AAA0000",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        viewModel.onPlacaChanged("AAA0000")
        viewModel.setPlaca()
        val state = viewModel.uiState.value

        assertEquals(
            state.flagAccess,
            true
        )
        assertEquals(
            state.flagDialog,
            false
        )

        assertEquals(
            state.placa,
            "AAA0000"
        )
        assertEquals(
            state.checkGetPlaca,
            true
        )
    }

    @Test
    fun `Check return failure if have error in GetPlaca`() = runTest {
        whenever(
            getPlacaVisitTerc(
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "GetPlacaVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )

        viewModel.recoverPlaca()
        val state = viewModel.uiState.value

        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            "PlacaVisitTercViewModel.recoverPlaca -> GetPlacaVisitTerc -> java.lang.Exception",
            state.failure
        )
        assertEquals(
            state.placa,
            ""
        )
        assertEquals(
            state.checkGetPlaca,
            true
        )
        assertEquals(
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return placa if GetPlaca execute successfully`() = runTest {
        whenever(
            getPlacaVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("AAA0000")
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )

        viewModel.recoverPlaca()
        val state = viewModel.uiState.value

        assertEquals(
            "AAA0000",
            state.placa
        )
        assertEquals(
            state.checkGetPlaca,
            false
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
}
