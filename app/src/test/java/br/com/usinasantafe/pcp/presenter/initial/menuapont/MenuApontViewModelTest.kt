package br.com.usinasantafe.pcp.presenter.initial.menuapont

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.common.CloseAllMov
import br.com.usinasantafe.pcp.domain.usecases.common.GetStatusSend
import br.com.usinasantafe.pcp.domain.usecases.initial.GetFlowList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MenuApontViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getFlowList = mock<GetFlowList>()
    private val getHeader = mock<GetHeader>()
    private val closeAllMov = mock<CloseAllMov>()
    private val getStatusSend = mock<GetStatusSend>()

    private fun getViewModel() = MenuApontViewModel(
        getFlowList = getFlowList,
        getHeader = getHeader,
        closeAllMov = closeAllMov,
        getStatusSend = getStatusSend
    )

    @Test
    fun `Check return failure if recoverHeader have failure`() = runTest {
        whenever(
            getHeader()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.returnHeader()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if closeAllMovOpen have failure`() = runTest {
        whenever(
            closeAllMov()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.closeAllMovOpen()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> CloseAllMovOpen -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return Splash if closeAllMovOpen execute correctly`() = runTest {
        whenever(
            closeAllMov()
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel()
        viewModel.closeAllMovOpen()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagReturn,
            true
        )
    }

    @Test
    fun `FlowList - Check return failure if have error in GetFlowList`() =
        runTest {
            whenever(
                getFlowList()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val viewModel = getViewModel()
            viewModel.flowList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> GetFlowList -> java.lang.Exception"
            )
        }

    @Test
    fun `FlowList - Check return list if GetFlowList execute successfully`() =
        runTest {
            whenever(
                getFlowList()
            ).thenReturn(
                Result.success(
                    listOf(
                        Fluxo(
                            idFluxo = 1,
                            descrFluxo = "MOV. EQUIP. PRÓPRIO"
                        )
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.flowList()
            val list = viewModel.uiState.value.flows
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.descrFluxo,
                "MOV. EQUIP. PRÓPRIO"
            )
        }

}