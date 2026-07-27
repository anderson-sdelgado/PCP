package br.com.usinasantafe.pcp.presenter.visitterc.destino

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetDestinoVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.SetDestinoVisitTerc
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
class DestinoVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setDestinoVisitTerc = mock<SetDestinoVisitTerc>()
    private val getDestinoVisitTerc = mock<GetDestinoVisitTerc>()

    private fun getViewModel(
        savedStateHandle: SavedStateHandle
    ) = DestinoVisitTercViewModel(
        savedStateHandle,
        setDestinoVisitTerc,
        getDestinoVisitTerc
    )

    @Test
    fun `Check return failure if have error in GetDestino`() = runTest {
        whenever(
            getDestinoVisitTerc(
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "GetDestinoVisitTerc",
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
        viewModel.recoverDestino()
        val state = viewModel.uiState.value

        // PADRONIZADO: Asserts individuais multi-linha
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha)
            "DestinoVisitTercViewModel.recoverDestino -> GetDestinoVisitTerc -> java.lang.Exception",
            state.failure
        )
    }

    @Test
    fun `Check return destino if GetDestino execute successfully`() = runTest {
        whenever(
            getDestinoVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("Destino")
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )
        viewModel.recoverDestino()
        val state = viewModel.uiState.value

        // PADRONIZADO: Asserts individuais multi-linha
        assertEquals( // expected, actual (string)
            "Destino",
            state.destino
        )
        assertEquals( // expected, actual (boolean - consistência com Cpf)
            false,
            state.checkGetDestino
        )
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            false
        )
        
    }

    @Test
    fun `Check return failure if destino is empty`() {
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )
        viewModel.setDestino()
        val state = viewModel.uiState.value

        // PADRONIZADO: Asserts individuais multi-linha
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        
    }

    @Test
    fun `Check return failure if have error in SetDestino`() = runTest {
        whenever(
            setDestinoVisitTerc(
                destino = "Destino",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "SetDestinoVisitTerc",
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
        viewModel.onDestinoChanged("Destino")
        viewModel.setDestino()
        val state = viewModel.uiState.value

        // PADRONIZADO: Asserts individuais multi-linha
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha)
            "DestinoVisitTercViewModel.setDestino -> SetDestinoVisitTerc -> java.lang.Exception",
            state.failure
        )
    }


    @Test
    fun `Check return true if SetDestino execute successfully`() = runTest {
        whenever(
            setDestinoVisitTerc(
                destino = "Destino",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            )
        )
        viewModel.onDestinoChanged("Destino")
        viewModel.setDestino()
        val state = viewModel.uiState.value

        // PADRONIZADO: Asserts individuais multi-linha
        assertEquals( // actual, expected (boolean)
            state.flagAccess,
            true
        )
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            false
        )
        
    }
}
