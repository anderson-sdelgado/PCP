package br.com.usinasantafe.pcp.presenter.chaveequip.controlelist

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.GetMovChaveEquipInsideList
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.StartReceiptMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.presenter.chaveequip.model.ControleChaveEquipModel
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ControleChaveEquipListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getHeader = mock<GetHeader>()
    private val getMovChaveEquipInsideList = mock<GetMovChaveEquipInsideList>()
    private val startReceiptMovChaveEquip = mock<StartReceiptMovChaveEquip>()

    private fun getViewModel() = ControleChaveEquipListViewModel(
        getHeader,
        getMovChaveEquipInsideList,
        startReceiptMovChaveEquip
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
    fun `Check return failure if have error in GetControleChaveEquipRemoveList`() =
        runTest {
            whenever(
                getMovChaveEquipInsideList()
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
                "Failure Usecase -> GetControleChaveEquipRemoveList -> java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if GetControleChaveRemoveList execute successfully`() =
        runTest {
            whenever(
                getMovChaveEquipInsideList()
            ).thenReturn(
                Result.success(
                    listOf(
                        ControleChaveEquipModel(
                            id = 1,
                            equip = "200 - TRATOR",
                            dthr = "04/12/2024 10:20",
                            colab = "19795 - ANDERSON DA SILVA DELGADO"
                        )
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.recoverMovList()
            val entityList = viewModel.uiState.value.controleChaveEquipModelList
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
                entity.equip,
                "200 - TRATOR"
            )
        }

    @Test
    fun `Check return failure if have error in StartRemoveChave`() =
        runTest {
            whenever(
                startReceiptMovChaveEquip()
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
                "Failure Usecase -> StartReceiptMovChaveEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if StartRemoveChave execute successfully`() =
        runTest {
            whenever(
                startReceiptMovChaveEquip()
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