package br.com.usinasantafe.pcp.presenter.chave.controlelist

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.chave.GetMovChaveInsideList
import br.com.usinasantafe.pcp.domain.usecases.chave.StartRemoveMovChave
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.presenter.chave.model.ControleChaveModel
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ControleChaveListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getHeader = mock<GetHeader>()
    private val getMovChaveInsideList = mock<GetMovChaveInsideList>()
    private val startRemoveMovChave = mock<StartRemoveMovChave>()

    private val viewModel = ControleChaveListViewModel(
        getHeader = getHeader,
        getMovChaveInsideList = getMovChaveInsideList,
        startRemoveMovChave = startRemoveMovChave
    )

    @Test
    fun `Check return failure if have error in getHeader`() = runTest {
        whenever(
            getHeader()
        ).thenReturn(
            resultFailure(
                "GetHeader",
                "-",
                Exception()
            )
        )
        viewModel.returnHeader()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "ControleChaveListViewModel.returnHeader -> GetHeader -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if GetHeader execute successfully`() =
        runTest {
            whenever(
                getHeader()
            ).thenReturn(
                Result.success(
                    HeaderModel(
                        descrVigia = "19759 - Anderson da Silva Delgado",
                        descrLocal = "1 - Usina"
                    )
                )
            )
            viewModel.returnHeader()
            assertEquals(
                viewModel.uiState.value.descrVigia,
                "19759 - Anderson da Silva Delgado"
            )
            assertEquals(
                viewModel.uiState.value.descrLocal,
                "1 - Usina"
            )
        }

    @Test
    fun `Check return failure if have error in GetMovChaveInsideList`() =
        runTest {
            whenever(
                getMovChaveInsideList()
            ).thenReturn(
                resultFailure(
                    "GetMovChaveInsideList",
                    "-",
                    Exception()
                )
            )
            viewModel.recoverMovList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ControleChaveListViewModel.recoverMovList -> GetMovChaveInsideList -> java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if GetMovChaveInsideList execute successfully`() =
        runTest {
            whenever(
                getMovChaveInsideList()
            ).thenReturn(
                Result.success(
                    listOf(
                        ControleChaveModel(
                            id = 1,
                            chave = "01 - SALA TI - TI",
                            dthr = "04/12/2024 10:20",
                            colab = "19795 - ANDERSON DA SILVA DELGADO"
                        )
                    )
                )
            )
            viewModel.recoverMovList()
            val entityList = viewModel.uiState.value.controleChaveModelList
            assertEquals(
                entityList.size,
                1
            )
            val entity = entityList[0]
            assertEquals(
                entity.id,
                1
            )
            assertEquals(
                entity.chave,
                "01 - SALA TI - TI"
            )
        }

    @Test
    fun `Check return failure if have error in StartRemoveChave`() =
        runTest {
            whenever(
                startRemoveMovChave()
            ).thenReturn(
                resultFailure(
                    "StartRemoveChave",
                    "-",
                    Exception()
                )
            )
            viewModel.startMov()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ControleChaveListViewModel.startMov -> StartRemoveChave -> java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if StartRemoveChave execute successfully`() =
        runTest {
            whenever(
                startRemoveMovChave()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.startMov()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
        }
}