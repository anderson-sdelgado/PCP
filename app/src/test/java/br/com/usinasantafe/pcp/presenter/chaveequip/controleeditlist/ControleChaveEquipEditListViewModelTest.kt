package br.com.usinasantafe.pcp.presenter.chaveequip.controleeditlist

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.CloseAllMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.GetMovChaveEquipOpenList
import br.com.usinasantafe.pcp.presenter.chaveequip.model.ControleChaveEquipModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ControleChaveEquipEditListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getMovChaveEquipOpenList = mock<GetMovChaveEquipOpenList>()
    private val closeAllMovChaveEquip = mock<CloseAllMovChaveEquip>()
    private val getViewModel = ControleChaveEquipEditListViewModel(
        getMovChaveEquipOpenList,
        closeAllMovChaveEquip
    )

    @Test
    fun `recoverControleChaveEquipOpenList - Check return failure if have error in GetMovChaveEquipOpenList`() =
        runTest {
            whenever(
                getMovChaveEquipOpenList()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            getViewModel.recoverMovOpenList()
            val state = getViewModel.uiState.value
            assertEquals(
                state.flagDialog,
                true
            )
            assertEquals(
                state.failure,
                "Failure Usecase -> GetMovChaveEquipOpenList -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverControleChaveEquipOpenList - Check return correct if function execute successfully`() =
        runTest {
            val list = listOf(
                ControleChaveEquipModel(
                    id = 1,
                    dthr = "08/08/2024 12:00",
                    tipoMov = "RETIRADA",
                    equip = "Trator",
                    colab = "Anderson da Silva Delgado"
                )
            )
            whenever(
                getMovChaveEquipOpenList()
            ).thenReturn(
                Result.success(
                    list
                )
            )
            getViewModel.recoverMovOpenList()
            val state = getViewModel.uiState.value
            assertEquals(
                state.flagDialog,
                false
            )
            val listState = state.controleChaveEquipModelList
            assertEquals(
                listState.size,
                1
            )
            val model = listState[0]
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.dthr,
                "08/08/2024 12:00"
            )
            assertEquals(
                model.tipoMov,
                "RETIRADA"
            )
            assertEquals(
                model.equip,
                "Trator"
            )
            assertEquals(
                model.colab,
                "Anderson da Silva Delgado"
            )
        }

    @Test
    fun `closeAllMov - Check return failure if have error in CloseAllMovChaveEquip`() =
        runTest {
            whenever(
                closeAllMovChaveEquip()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            getViewModel.closeAllMov()
            val state = getViewModel.uiState.value
            assertEquals(
                state.flagDialog,
                true
            )
            assertEquals(
                state.failure,
                "Failure Usecase -> CloseAllMovChaveEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `closeAllMov - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                closeAllMovChaveEquip()
            ).thenReturn(
                Result.success(
                    true
                )
            )
            getViewModel.closeAllMov()
            val state = getViewModel.uiState.value
            assertEquals(
                state.flagDialog,
                false
            )
            assertEquals(
                state.flagCloseAllMov,
                true
            )
        }
}