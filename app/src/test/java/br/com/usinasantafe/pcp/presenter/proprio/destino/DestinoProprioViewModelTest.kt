package br.com.usinasantafe.pcp.presenter.proprio.destino

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetDestinoProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetTypeMov
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SetDestinoProprio
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DestinoProprioViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    private val setDestinoProprio = mock<SetDestinoProprio>()
    private val getDestinoProprio = mock<GetDestinoProprio>()
    private val getTypeMov = mock<GetTypeMov>()
    private val viewModel = DestinoProprioViewModel(
        SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                Args.ID_ARGS to 0,
            )
        ),
        setDestinoProprio,
        getDestinoProprio,
        getTypeMov
    )

    @Test
    fun `Check view msg if field is empty`() {
        viewModel.setDestino()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
    }

    @Test
    fun `Check return failure if have failure in SetDestino`() = runTest {
        whenever(
            setDestinoProprio(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "SetDestinoProprio",
                "-",
                Exception()
            )
        )
        viewModel.onDestinoChanged("Teste")
        viewModel.setDestino()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "DestinoProprioViewModel.setDestino -> SetDestinoProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have failure in GetTypeMov`() = runTest {
        whenever(
            setDestinoProprio(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getTypeMov()
        ).thenReturn(
            resultFailure(
                "GetTypeMov",
                "-",
                Exception()
            )
        )
        viewModel.onDestinoChanged("Teste")
        viewModel.setDestino()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            false
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "DestinoProprioViewModel.setDestino -> GetTypeMov -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return TypeMov INPUT if execute success`() = runTest {
        whenever(
            setDestinoProprio(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getTypeMov()
        ).thenReturn(
            Result.success(TypeMovEquip.INPUT)
        )
        viewModel.onDestinoChanged("Teste")
        viewModel.setDestino()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            true
        )
        assertEquals(
            viewModel.uiState.value.typeMov,
            TypeMovEquip.INPUT
        )
    }

    @Test
    fun `Check return failure if have failure in GetDestino`() = runTest {
        whenever(
            getDestinoProprio(
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "GetDestinoProprio",
                "-",
                Exception()
            )
        )
        val viewModel = DestinoProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1,
                )
            ),
            setDestinoProprio,
            getDestinoProprio,
            getTypeMov
        )
        viewModel.getDestino()
        val state = viewModel.uiState.value
        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            state.failure,
            "DestinoProprioViewModel.getDestino -> GetDestinoProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return destino if GetDestino execute success`() = runTest {
        whenever(
            getDestinoProprio(
                id = 1
            )
        ).thenReturn(
            Result.success("Destino")
        )
        val viewModel = DestinoProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1,
                )
            ),
            setDestinoProprio,
            getDestinoProprio,
            getTypeMov
        )
        viewModel.getDestino()
        val state = viewModel.uiState.value
        assertEquals(
            state.flagGetDestino,
            false
        )
        assertEquals(
            state.destino,
            "Destino"
        )
    }
}