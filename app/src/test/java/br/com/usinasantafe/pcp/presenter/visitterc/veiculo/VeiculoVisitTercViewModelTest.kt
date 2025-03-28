package br.com.usinasantafe.pcp.presenter.visitterc.veiculo

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.visitterc.GetVeiculoVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.SetVeiculoVisitTerc
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class VeiculoVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if fields is empty`() {
        val setVeiculoVisitTerc = mock<SetVeiculoVisitTerc>()
        val getVeiculoVisitTerc = mock<GetVeiculoVisitTerc>()
        val viewModel = VeiculoVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setVeiculoVisitTerc,
            getVeiculoVisitTerc
        )
        viewModel.setVeiculo()
        assertEquals(viewModel.uiState.value.flagDialog, true)
    }

    @Test
    fun `Check return failure if have error in setVeiculo`() = runTest {
        val setVeiculoVisitTerc = mock<SetVeiculoVisitTerc>()
        val getVeiculoVisitTerc = mock<GetVeiculoVisitTerc>()
        whenever(
            setVeiculoVisitTerc(
                veiculo = "GOL",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = VeiculoVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setVeiculoVisitTerc,
            getVeiculoVisitTerc
        )
        viewModel.onVeiculoChanged("GOL")
        viewModel.setVeiculo()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> SetVeiculoVisitTerc -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if setVeiculo execute successfully`() = runTest {
        val setVeiculoVisitTerc = mock<SetVeiculoVisitTerc>()
        val getVeiculoVisitTerc = mock<GetVeiculoVisitTerc>()
        whenever(
            setVeiculoVisitTerc(
                veiculo = "GOL",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = VeiculoVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setVeiculoVisitTerc,
            getVeiculoVisitTerc
        )
        viewModel.onVeiculoChanged("GOL")
        viewModel.setVeiculo()
        assertTrue(viewModel.uiState.value.flagAccess)
    }

    @Test
    fun `Check return failure if have error in GetVeiculo`() = runTest {
        val setVeiculoVisitTerc = mock<SetVeiculoVisitTerc>()
        val getVeiculoVisitTerc = mock<GetVeiculoVisitTerc>()
        whenever(
            getVeiculoVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = VeiculoVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            setVeiculoVisitTerc,
            getVeiculoVisitTerc
        )
        viewModel.recoverVeiculo()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> GetVeiculoVisitTerc -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return veiculo if GetVeiculo execute successfully`() = runTest {
        val setVeiculoVisitTerc = mock<SetVeiculoVisitTerc>()
        val getVeiculoVisitTerc = mock<GetVeiculoVisitTerc>()
        whenever(
            getVeiculoVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("GOL")
        )
        val viewModel = VeiculoVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            setVeiculoVisitTerc,
            getVeiculoVisitTerc
        )
        viewModel.recoverVeiculo()
        val state = viewModel.uiState.value
        assertEquals(state.veiculo, "GOL")
    }
}