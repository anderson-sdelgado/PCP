package br.com.usinasantafe.pcp.presenter.proprio.movlist

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.CloseAllMovProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetMovEquipProprioOpenList
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.StartMovEquipProprio
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovEquipProprioViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getHeader = mock<GetHeader>()
    private val startMovEquipProprio = mock<StartMovEquipProprio>()
    private val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
    private val closeAllMovProprio = mock<CloseAllMovProprio>()
    private val viewModel = MovEquipProprioListViewModel(
        getHeader,
        startMovEquipProprio,
        getMovEquipProprioOpenList,
        closeAllMovProprio
    )

    @Test
    fun `Check return failure if recoverHeader have failure`() = runTest {
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
            "MovEquipProprioListViewModel.returnHeader -> GetHeader -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return nome Vigia if recoverHeader execute correctly`() = runTest {
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
    fun `Check return failure if startMovEquipProprio have failure`() = runTest {
        whenever(
            startMovEquipProprio(
                typeMov = TypeMovEquip.INPUT
            )
        ).thenReturn(
            resultFailure(
                "StartMovEquipProprio",
                "-",
                Exception()
            )
        )
        viewModel.startMov(typeMov = TypeMovEquip.INPUT)
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "MovEquipProprioListViewModel.startMov -> StartMovEquipProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return success if startMovEquipProprio execute correctly`() = runTest {
        whenever(
            startMovEquipProprio(
                typeMov = TypeMovEquip.INPUT
            )
        ).thenReturn(
            Result.success(true)
        )
        viewModel.startMov(
            typeMov = TypeMovEquip.INPUT
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            true
        )
    }

    @Test
    fun `Check return failure if RecoverMovEquipProprioOpen have failure`() = runTest {
        whenever(
            getMovEquipProprioOpenList()
        ).thenReturn(
            resultFailure(
                "GetMovEquipProprioOpenList",
                "-",
                Exception()
            )
        )
        viewModel.recoverMovEquipOpenList()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "MovEquipProprioListViewModel.recoverMovEquipOpenList -> GetMovEquipProprioOpenList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return emptyList if not have mov open`() = runTest {
        whenever(
            getMovEquipProprioOpenList()
        ).thenReturn(
            Result.success(
                emptyList()
            )
        )
        viewModel.recoverMovEquipOpenList()
        assertEquals(
            viewModel.uiState.value.movEquipProprioModelList.isEmpty(),
            true
        )
    }

    @Test
    fun `Check return list mov if have mov open`() = runTest {
        val movEquipProprioViewModel = MovEquipProprioModel(
            id = 1,
            dthr = "08/08/2024 12:00",
            typeMov = "ENTRADA",
            equip = "2300",
            colab = "19759 - ANDERSON DA SILVA DELGADO"
        )
        whenever(
            getMovEquipProprioOpenList()
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprioViewModel
                )
            )
        )
        viewModel.recoverMovEquipOpenList()
        assertEquals(
            viewModel.uiState.value.movEquipProprioModelList.size,
            1
        )
        assertEquals(
            viewModel.uiState.value.movEquipProprioModelList[0],
            movEquipProprioViewModel
        )
    }

    @Test
    fun `Check return failure if closeAllMovProprioOpen have failure`() = runTest {
        whenever(
            closeAllMovProprio()
        ).thenReturn(
            resultFailure(
                "CloseAllMovProprio",
                "-",
                Exception()
            )
        )
        viewModel.closeAllMov()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "MovEquipProprioListViewModel.closeAllMov -> CloseAllMovProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if closeAllMovProprioOpen execute correctly`() = runTest {
        whenever(
            closeAllMovProprio()
        ).thenReturn(
            Result.success(true)
        )
        viewModel.closeAllMov()
        assertEquals(
            viewModel.uiState.value.flagCloseAllMov,
            true
        )
    }
}