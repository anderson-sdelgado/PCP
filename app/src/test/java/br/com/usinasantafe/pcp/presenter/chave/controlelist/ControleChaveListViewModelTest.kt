package br.com.usinasantafe.pcp.presenter.chave.controlelist

import br.com.usinasantafe.pcp.MainCoroutineRule
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
class ControleChaveListGetViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getHeader = mock<GetHeader>()
    private val getMovChaveInsideList = mock<GetMovChaveInsideList>()
    private val startRemoveMovChave = mock<StartRemoveMovChave>()

    private fun getViewModel() = ControleChaveListViewModel(
        getHeader = getHeader,
        getMovChaveInsideList = getMovChaveInsideList,
        startRemoveMovChave = startRemoveMovChave
    )

    @Test
    fun `Check return failure if have error in getHeader`() = runTest {
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
            val viewModel = getViewModel()
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
    fun `Check return failure if have error in GetControleChaveRemoveList`() =
        runTest {
            whenever(
                getMovChaveInsideList()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val viewModel = getViewModel()
            viewModel.recoverMovList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> GetControleChaveRemoveList -> java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if GetControleChaveRemoveList execute successfully`() =
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
            val viewModel = getViewModel()
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
                Result.failure(
                    Exception()
                )
            )
            val viewModel = getViewModel()
            viewModel.startMov()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> StartRemoveChave -> java.lang.Exception"
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
            val viewModel = getViewModel()
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