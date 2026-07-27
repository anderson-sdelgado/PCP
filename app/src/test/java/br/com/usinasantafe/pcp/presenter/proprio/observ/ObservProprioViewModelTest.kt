package br.com.usinasantafe.pcp.presenter.proprio.observ

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetObservProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetTypeMov
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SaveMovEquipProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SetObservProprio
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Ou use esta importação
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ObservProprioViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setObservProprio = mock<SetObservProprio>()
    private val getObservProprio = mock<GetObservProprio>()
    private val saveMovEquipProprio = mock<SaveMovEquipProprio>()
    private val getTypeMov = mock<GetTypeMov>()

    private fun createViewModel(savedStateHandle: SavedStateHandle) = ObservProprioViewModel(
        savedStateHandle,
        setObservProprio,
        getObservProprio,
        saveMovEquipProprio,
        getTypeMov
    )

    @Test
    fun `Check return failure if have failure in GetTypeMov`() = runTest {
        whenever(
            getTypeMov()
        ).thenReturn(
            resultFailure(
                "GetTypeMov",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setReturn()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        // PADRONIZADO
        assertEquals(
            viewModel.uiState.value.failure,
            "ObservProprioViewModel.setReturn -> GetTypeMov -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return TypeMov if GetTypeMov execute success`() = runTest {
        whenever(
            getTypeMov()
        ).thenReturn(
            Result.success(TypeMovEquip.INPUT)
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setReturn()
        assertEquals(viewModel.uiState.value.flagReturn, true)
        assertEquals(viewModel.uiState.value.typeMov, TypeMovEquip.INPUT)
    }

    @Test
    fun `Check return access if execute SetObservProprio with field empty`() = runTest {
        // Mock para SaveMovEquipProprio retornar sucesso, pois é chamado mesmo com observ vazio
        whenever(
            saveMovEquipProprio()).thenReturn(Result.success(true))
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setObserv()
        assertEquals(viewModel.uiState.value.flagAccess, true)
        assertFalse(viewModel.uiState.value.flagDialog) // Garante que não houve diálogo de erro
    }

    @Test
    fun `Check return failure if have errors in SetObservProprio`() = runTest {
        whenever(
            setObservProprio(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "SetObservProprio",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.onObservChanged("Teste")
        viewModel.setObserv()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        // PADRONIZADO
        assertEquals(
            viewModel.uiState.value.failure,
            "ObservProprioViewModel.setObserv -> SetObservProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure with observ empty if have errors in SaveMovEquipProprio`() = runTest {
        // Mock para SetObservProprio não ser chamado ou retornar sucesso (não relevante aqui)
        // O importante é a falha no SaveMovEquipProprio
        whenever(
            saveMovEquipProprio()
        ).thenReturn(
            resultFailure(
                "SaveMovEquipProprio",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setObserv() // Chama com observ vazio
        assertEquals(viewModel.uiState.value.flagDialog, true)
        // PADRONIZADO
        assertEquals(
            viewModel.uiState.value.failure,
            "ObservProprioViewModel.setObserv -> SaveMovEquipProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have errors in SaveMovEquipProprio`() = runTest {
        // Mock para SetObservProprio retornar sucesso
        whenever(
            setObservProprio(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        // Mock para SaveMovEquipProprio retornar falha
        whenever(
            saveMovEquipProprio()
        ).thenReturn(
            resultFailure(
                "SaveMovEquipProprio",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.onObservChanged("Teste")
        viewModel.setObserv()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        // PADRONIZADO
        assertEquals(
            viewModel.uiState.value.failure,
            "ObservProprioViewModel.setObserv -> SaveMovEquipProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return access if SetObservProprio execute success`() = runTest {
        whenever(
            setObservProprio(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(saveMovEquipProprio()).thenReturn(
            Result.success(true)
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.onObservChanged("Teste")
        viewModel.setObserv()
        assertEquals(viewModel.uiState.value.flagAccess, true)
        assertFalse(viewModel.uiState.value.flagDialog) // Garante que não houve diálogo de erro
    }

    @Test
    fun `Check return access with observ empty if SaveMovEquipProprio execute success`() = runTest {
        // Mock para SetObservProprio não ser chamado ou retornar sucesso (não relevante aqui)
        // O importante é o sucesso no SaveMovEquipProprio
        whenever(saveMovEquipProprio()).thenReturn(
            Result.success(true)
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setObserv() // Chama com observ vazio
        assertEquals(viewModel.uiState.value.flagAccess, true)
        assertFalse(viewModel.uiState.value.flagDialog) // Garante que não houve diálogo de erro
    }

    @Test
    fun `Check return failure if have error in GetObservProprio`() = runTest {
        whenever(
            getObservProprio(
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "GetObservProprio",
                "-",
                Exception()
            )
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1,
                )
            )
        )
        viewModel.getObserv()
        assertTrue(viewModel.uiState.value.flagDialog)
        // PADRONIZADO
        assertEquals(
            viewModel.uiState.value.failure,
            "ObservProprioViewModel.getObserv -> GetObservProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return observ if GetObservProprio execute success`() = runTest {
        whenever(
            getObservProprio(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        val viewModel = createViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1,
                )
            )
        )
        viewModel.getObserv()
        val state = viewModel.uiState.value
        assertFalse(state.flagDialog) // Garante que não houve diálogo de erro
        assertEquals(state.observ, "Observação")
    }
}
