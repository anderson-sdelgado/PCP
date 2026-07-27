package br.com.usinasantafe.pcp.presenter.proprio.equipseglist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.CleanEquipSeg
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.DeleteEquipSeg
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetEquipSegList
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeEquip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class EquipSegListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val cleanEquipSeg = mock<CleanEquipSeg>()
    private val getEquipSegList = mock<GetEquipSegList>()
    private val deleteEquipSeg = mock<DeleteEquipSeg>()
    private val viewModel = EquipSegListViewModel(
        SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                Args.ID_ARGS to 0,
            )
        ),
        cleanEquipSeg,
        getEquipSegList,
        deleteEquipSeg
    )

    @Test
    fun `Check return failure if CleanEquipSeg have failure`() = runTest {
        whenever(
            cleanEquipSeg()
        ).thenReturn(
            resultFailure(
                "CleanEquipSeg",
                "-",
                Exception()
            )
        )
        viewModel.cleanVeicSeg()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "EquipSegListViewModel.cleanVeicSeg -> CleanEquipSeg -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have failure in RecoverEquipSeg`() = runTest {
        whenever(
            getEquipSegList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            resultFailure(
                "GetEquipSegList",
                "-",
                Exception()
            )
        )
        viewModel.recoverVeicSeg()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "EquipSegListViewModel.recoverVeicSeg -> GetEquipSegList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list Equip if RecoverEquipSeg execute successfully`() = runTest {
        whenever(
            getEquipSegList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    Equip(
                        idEquip = 10,
                        nroEquip = 100,
                        descrEquip = "teste"
                    ),
                    Equip(
                        idEquip = 20,
                        nroEquip = 200,
                        descrEquip = "teste"
                    )
                )
            )
        )
        viewModel.recoverVeicSeg()
        assertEquals(
            viewModel.uiState.value.equipSegList.size,
            2
        )
        assertEquals(
            viewModel.uiState.value.equipSegList[0].idEquip,
            10
        )
    }

    @Test
    fun `Check return failure if have failure in DeleteEquipSeg`() = runTest {
        whenever(
            deleteEquipSeg(
                10,
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            resultFailure(
                "DeleteEquipSeg",
                "-",
                Exception()
            )
        )
        viewModel.setDelete(10)
        assertEquals(
            viewModel.uiState.value.flagDialogCheck,
            true
        )
        viewModel.deleteVeicSeg()
        assertEquals(
            viewModel.uiState.value.flagDialogCheck,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "EquipSegListViewModel.deleteVeicSeg -> DeleteEquipSeg -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list Colab after deleteEquipSeg execute successfully`() = runTest {
        whenever(
            deleteEquipSeg(
                10,
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getEquipSegList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    Equip(
                        idEquip = 10,
                        nroEquip = 100,
                        descrEquip = "teste"
                    ),
                    Equip(
                        idEquip = 20,
                        nroEquip = 200,
                        descrEquip = "teste"
                    )
                )
            )
        )
        viewModel.setDelete(10)
        assertEquals(
            viewModel.uiState.value.flagDialogCheck,
            true
        )
        viewModel.deleteVeicSeg()
        assertEquals(
            viewModel.uiState.value.flagDialogCheck,
            false
        )
        assertEquals(
            viewModel.uiState.value.equipSegList.size,
            2
        )
        assertEquals(
            viewModel.uiState.value.equipSegList[0].idEquip,
            10
        )
    }

}