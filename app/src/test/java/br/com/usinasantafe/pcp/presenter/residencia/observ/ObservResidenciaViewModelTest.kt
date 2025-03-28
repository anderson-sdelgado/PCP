package br.com.usinasantafe.pcp.presenter.residencia.observ

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.residencia.GetObservResidencia
import br.com.usinasantafe.pcp.domain.usecases.residencia.SaveMovEquipResidencia
import br.com.usinasantafe.pcp.domain.usecases.residencia.SetObservResidencia
import br.com.usinasantafe.pcp.domain.usecases.residencia.StartOutputMovEquipResidencia
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ObservResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setObservResidencia = mock<SetObservResidencia>()
    private val getObservResidencia = mock<GetObservResidencia>()
    private val startOutputMovEquipResidencia = mock<StartOutputMovEquipResidencia>()
    private val saveMovEquipResidencia = mock<SaveMovEquipResidencia>()

    private fun getViewModel(savedStateHandle: SavedStateHandle) =
        ObservResidenciaViewModel(
            savedStateHandle,
            setObservResidencia,
            getObservResidencia,
            startOutputMovEquipResidencia,
            saveMovEquipResidencia
        )

    @Test
    fun `Check return failure if have error in GetObserv`() = runTest {
        whenever(
            getObservResidencia(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.recoverObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> GetObservResidencia -> java.lang.Exception")
    }

    @Test
    fun `Check return observ if GetObserv execute successfully`() = runTest {
        whenever(
            getObservResidencia(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.recoverObserv()
        val state = viewModel.uiState.value
        assertEquals(state.observ, "Observação")
    }

    @Test
    fun `Check return failure if have error in StartOutputMovEquipResidencia`() = runTest {
        whenever(
            startOutputMovEquipResidencia(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.OUTPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> StartOutputMovEquipResidencia -> java.lang.Exception")
    }

    @Test
    fun `Check return failure if have error in SetObserv`() = runTest {
        whenever(
            setObservResidencia(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 0
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> SetObservResidencia -> java.lang.Exception")
    }

    @Test
    fun `Check return failure if have error in SaveMovEquipResidencia`() = runTest {
        whenever(
            setObservResidencia(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            saveMovEquipResidencia(
                typeMov = TypeMovEquip.INPUT,
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> SaveMovEquipResidencia -> java.lang.Exception")
    }

    @Test
    fun `Check access true if SetObserv execute successfully`() = runTest {
        whenever(
            setObservResidencia(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            saveMovEquipResidencia(
                typeMov = TypeMovEquip.INPUT,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagAccess)
    }
}