package br.com.usinasantafe.pcp.presenter.visitterc.passaglist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.CleanPassagVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.DeletePassagVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetPassagVisitTercList
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeOcupante
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PassagVisitTercListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val cleanPassagVisitTerc = mock<CleanPassagVisitTerc>()
    private val getPassagVisitTercList = mock<GetPassagVisitTercList>()
    private val deletePassagVisitTerc = mock<DeletePassagVisitTerc>()

    private fun getViewModel(
        savedStateHandle: SavedStateHandle
    ) = PassagVisitTercListViewModel(
        savedStateHandle,
        cleanPassagVisitTerc,
        getPassagVisitTercList,
        deletePassagVisitTerc
    )

    @Test
    fun `Check return failure if have error in CleanPassagVisitTerc`() = runTest {
        whenever(
            cleanPassagVisitTerc()
        ).thenReturn(
            resultFailure(
                "CleanPassagVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        viewModel.cleanPassag()
        val state = viewModel.uiState.value

        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            "PassagVisitTercListViewModel.cleanPassag -> CleanPassagVisitTerc -> java.lang.Exception",
            state.failure
        )
        assertEquals(
            state.flagClean,
            true
        )
        assertEquals(
            state.flagDialogCheck,
            false
        )
        assertEquals(
            state.passagList,
            emptyList<PassagVisitTercModel>()
        )
    }

    @Test
    fun `Check return true if CleanPassagVisitTerc execute successfully`() = runTest {
        whenever(
            cleanPassagVisitTerc()
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        viewModel.cleanPassag()
        val state = viewModel.uiState.value

        assertEquals(
            state.flagClean,
            false
        )
        assertEquals(
            state.flagDialog,
            false
        )

        assertEquals(
            state.flagDialogCheck,
            false
        )
        assertEquals(
            state.passagList,
            emptyList<PassagVisitTercModel>()
        )
    }

    @Test
    fun `Check return failure if have error in GetPassagVisitTercList`() = runTest {
        whenever(
            getPassagVisitTercList(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "GetPassagVisitTercList",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        viewModel.recoverPassag()
        val state = viewModel.uiState.value

        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            "PassagVisitTercListViewModel.recoverPassag -> GetPassagVisitTercList -> java.lang.Exception",
            state.failure
        )
        assertEquals(
            state.flagClean,
            true
        )
        assertEquals(
            state.flagDialogCheck,
            false
        )
        assertEquals(
            state.passagList,
            emptyList<PassagVisitTercModel>()
        )
    }

    @Test
    fun `Check return list if GetPassagVisitTercList execute successfully`() = runTest {
        val expectedList = listOf(
            PassagVisitTercModel(
                id = 1,
                cpf = "123.456.789-00",
                nome = "Nome"
            )
        )
        whenever(
            getPassagVisitTercList(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(expectedList)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        viewModel.recoverPassag()
        val state = viewModel.uiState.value

        assertEquals(
            expectedList,
            state.passagList
        )
        assertEquals(
            state.flagDialog,
            false
        )
        assertEquals(
            state.flagClean,
            true
        )
        assertEquals(
            state.flagDialogCheck,
            false
        )
    }

    @Test
    fun `Check return failure if have error in DeletePassagVisitTerc`() = runTest {
        whenever(
            deletePassagVisitTerc(
                idVisitTerc = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "DeletePassagVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        viewModel.setDelete(1)
        viewModel.deletePassag()
        val state = viewModel.uiState.value

        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            "PassagVisitTercListViewModel.deletePassag -> DeletePassagVisitTerc -> java.lang.Exception",
            state.failure
        )
        assertEquals(
            state.flagClean,
            true
        )
        assertEquals(
            state.flagDialogCheck,
            true
        )
        assertEquals(
            state.passagList,
            emptyList<PassagVisitTercModel>()
        )
    }

    @Test
    fun `Check return true if DeletePassagVisitTerc execute successfully`() = runTest {
        val expectedListAfterDelete = listOf(
            PassagVisitTercModel(
                id = 2, // Simula que o item 1 foi removido
                cpf = "987.654.321-00",
                nome = "Outro Nome"
            )
        )
        whenever(
            deletePassagVisitTerc(
                idVisitTerc = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getPassagVisitTercList(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(expectedListAfterDelete)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        viewModel.setDelete(1)
        viewModel.deletePassag()
        val state = viewModel.uiState.value

        assertEquals(
            expectedListAfterDelete,
            state.passagList
        )
        assertEquals(
            state.flagDialogCheck,
            false
        )
        assertEquals(
            state.flagDialog,
            false
        )
        assertEquals(
            state.flagClean,
            true
        )
    }
}
