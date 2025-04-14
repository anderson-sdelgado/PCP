package br.com.usinasantafe.pcp.presenter.proprio.detalhe

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.proprio.CloseMovProprio
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetDetalheProprio
import br.com.usinasantafe.pcp.presenter.Args
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetalheProprioViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getDetalheProprio = mock<GetDetalheProprio>()
    private val closeMovProprio = mock<CloseMovProprio>()
    private val viewModel = DetalheProprioViewModel(
        SavedStateHandle(
            mapOf(
                Args.ID_ARGS to 1
            )
        ),
        getDetalheProprio,
        closeMovProprio
    )

    @Test
    fun `Check return failure if have error in recoverDetalhe`() = runTest {
        whenever(
            getDetalheProprio(1)
        ).thenReturn(
            resultFailure(
                "GetDetalheProprio",
                "-",
                Exception()
            )
        )
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value
        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            state.failure,
            "DetalheProprioViewModel.recoverDetalhe -> GetDetalheProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return model if recoverDetalhe execute correctly`() = runTest {
        whenever(
            getDetalheProprio(1)
        ).thenReturn(
            Result.success(
                DetalheProprioModel(
                    dthr = "08/08/2024 12:00",
                    tipoMov = "ENTRADA",
                    veiculo = "2300",
                    veiculoSec = "2301 - 2302",
                    motorista = "19759 - ANDERSON DA SILVA DELGADO",
                    passageiro = "19035 - JOSE DONIZETE; 18017 - RONALDO;",
                    notaFiscal = "123456",
                    observ = "Teste Observ",
                    destino = "Teste Destino"
                )
            )
        )
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value
        assertEquals(
            state.dthr,
            "08/08/2024 12:00"
        )
        assertEquals(
            state.tipoMov,
            "ENTRADA"
        )
    }

    @Test
    fun `Check return failure if have error in CloseMovProprioOpen`() = runTest {
        whenever(
            closeMovProprio(1)
        ).thenReturn(
            resultFailure(
                "CloseMovProprio",
                "-",
                Exception()
            )
        )
        viewModel.closeMov()
        val state = viewModel.uiState.value
        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            state.failure,
            "DetalheProprioViewModel.closeMov -> CloseMovProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if CloseMovProprioOpen execute correctly`() = runTest {
        whenever(
            closeMovProprio(1)
        ).thenReturn(
            Result.success(true)
        )
        viewModel.closeMov()
        val state = viewModel.uiState.value
        assertEquals(
            state.flagCloseMov,
            true
        )
    }
}