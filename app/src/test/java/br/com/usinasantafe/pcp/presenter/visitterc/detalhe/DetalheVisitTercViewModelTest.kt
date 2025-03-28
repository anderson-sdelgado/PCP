package br.com.usinasantafe.pcp.presenter.visitterc.detalhe

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.visitterc.CloseMovVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.GetDetalheVisitTerc
import br.com.usinasantafe.pcp.presenter.Args
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetalheVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in recoverDetalhe`() = runTest {
        val getDetalheVisitTerc = mock<GetDetalheVisitTerc>()
        val closeMovVisitTerc = mock<CloseMovVisitTerc>()
        whenever(
            getDetalheVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = DetalheVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
            getDetalheVisitTerc,
            closeMovVisitTerc
        )
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> RecoverDetalheVisitTerc -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return model if recoverDetalhe execute correctly`() = runTest {
        val getDetalheVisitTerc = mock<GetDetalheVisitTerc>()
        val closeMovVisitTerc = mock<CloseMovVisitTerc>()
        whenever(getDetalheVisitTerc(1)).thenReturn(
            Result.success(
                DetalheVisitTercModel(
                    dthr = "08/08/2024 12:00",
                    tipoMov = "ENTRADA",
                    veiculo = "GOL",
                    placa = "AAA-0000",
                    tipoVisitTerc = "VISITANTE",
                    motorista = "19759 - ANDERSON DA SILVA DELGADO",
                    passageiro = "19035 - JOSE DONIZETE; 18017 - RONALDO;",
                    destino = "Teste Destino",
                    observ = "Teste Observ"
                )
            )
        )
        val viewModel = DetalheVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
            getDetalheVisitTerc,
            closeMovVisitTerc
        )
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value
        assertEquals(state.dthr, "08/08/2024 12:00")
        assertEquals(state.tipoMov, "ENTRADA")
    }

    @Test
    fun `Check return failure if have error in CloseMovVisitTercOpen`() = runTest {
        val getDetalheVisitTerc = mock<GetDetalheVisitTerc>()
        val closeMovVisitTerc = mock<CloseMovVisitTerc>()
        whenever(
            closeMovVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = DetalheVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
            getDetalheVisitTerc,
            closeMovVisitTerc
        )
        viewModel.closeMov()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> CloseMovVisitTercOpen -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if CloseMovVisitTercOpen execute correctly`() = runTest {
        val getDetalheVisitTerc = mock<GetDetalheVisitTerc>()
        val closeMovVisitTerc = mock<CloseMovVisitTerc>()
        whenever(closeMovVisitTerc(1)).thenReturn(
            Result.success(true)
        )
        val viewModel = DetalheVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
            getDetalheVisitTerc,
            closeMovVisitTerc
        )
        viewModel.closeMov()
        val state = viewModel.uiState.value
        assertTrue(state.flagCloseMov)
    }
}