package br.com.usinasantafe.pcp.presenter.visitterc.movlist

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.visitterc.GetMovEquipVisitTercInsideList
import br.com.usinasantafe.pcp.domain.usecases.visitterc.StartInputMovEquipVisitTerc
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import br.com.usinasantafe.pcp.presenter.visitterc.model.MovEquipVisitTercModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovEquipVisitTercListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in getHeader`() = runTest {
        val getHeader = mock<GetHeader>()
        val startInputMovEquipVisitTerc = mock<StartInputMovEquipVisitTerc>()
        val getMovEquipVisitTercInsideList = mock<GetMovEquipVisitTercInsideList>()
        whenever(
            getHeader()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = MovEquipVisitTercListViewModel(
            getHeader,
            getMovEquipVisitTercInsideList,
            startInputMovEquipVisitTerc
        )
        viewModel.returnHeader()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return Nome Vigia if getHeader execute correctly`() = runTest {
        val getHeader = mock<GetHeader>()
        val startInputMovEquipVisitTerc = mock<StartInputMovEquipVisitTerc>()
        val getMovEquipVisitTercInsideList = mock<GetMovEquipVisitTercInsideList>()
        whenever(getHeader()).thenReturn(
            Result.success(
                HeaderModel(
                    descrVigia = "19759 - Anderson da Silva Delgado",
                    descrLocal = "1 - Usina"
                )
            )
        )
        val viewModel = MovEquipVisitTercListViewModel(
            getHeader,
            getMovEquipVisitTercInsideList,
            startInputMovEquipVisitTerc
        )
        viewModel.returnHeader()
        assertEquals(viewModel.uiState.value.descrVigia, "19759 - Anderson da Silva Delgado")
        assertEquals(
            viewModel.uiState.value.descrLocal,
            "1 - Usina"
        )
    }

    @Test
    fun `Check return failure if have error in recoverMovEquipInputList`() = runTest {
        val getHeader = mock<GetHeader>()
        val startInputMovEquipVisitTerc = mock<StartInputMovEquipVisitTerc>()
        val getMovEquipVisitTercInsideList = mock<GetMovEquipVisitTercInsideList>()
        whenever(
            getMovEquipVisitTercInsideList()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = MovEquipVisitTercListViewModel(
            getHeader,
            getMovEquipVisitTercInsideList,
            startInputMovEquipVisitTerc
        )
        viewModel.recoverMovEquipList()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> GetMovEquipVisitTercInputOpenList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list if recoverMovEquipInputList execute correctly`() = runTest {
        val getHeader = mock<GetHeader>()
        val startInputMovEquipVisitTerc = mock<StartInputMovEquipVisitTerc>()
        val getMovEquipVisitTercInsideList = mock<GetMovEquipVisitTercInsideList>()
        whenever(
            getMovEquipVisitTercInsideList()
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipVisitTercModel(
                        id = 1,
                        dthr = "DATA/HORA: 08/08/2024 12:00",
                        motorista = "MOTORISTA: 326.949.728-88 - ANDERSON DA SILVA DELGADO",
                        veiculo = "VEÍCULO: GOL",
                        placa = "PLACA: ABC1234",
                        tipoVisitTerc = "VISITANTE"
                    )
                )
            )
        )
        val viewModel = MovEquipVisitTercListViewModel(
            getHeader,
            getMovEquipVisitTercInsideList,
            startInputMovEquipVisitTerc
        )
        viewModel.recoverMovEquipList()
        assertEquals(viewModel.uiState.value.movEquipVisitTercModelList.size, 1)
        assertEquals(
            viewModel.uiState.value.movEquipVisitTercModelList[0].dthr,
            "DATA/HORA: 08/08/2024 12:00"
        )
    }

    @Test
    fun `Check return failure if have error in startMovEquipVisitTerc`() = runTest {
        val getHeader = mock<GetHeader>()
        val startInputMovEquipVisitTerc = mock<StartInputMovEquipVisitTerc>()
        val getMovEquipVisitTercInsideList = mock<GetMovEquipVisitTercInsideList>()
        whenever(
            startInputMovEquipVisitTerc()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = MovEquipVisitTercListViewModel(
            getHeader,
            getMovEquipVisitTercInsideList,
            startInputMovEquipVisitTerc
        )
        viewModel.startMov()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> StartMovEquipVisitTerc -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if startMovEquipVisitTerc execute correctly`() = runTest {
        val getHeader = mock<GetHeader>()
        val startInputMovEquipVisitTerc = mock<StartInputMovEquipVisitTerc>()
        val getMovEquipVisitTercInsideList = mock<GetMovEquipVisitTercInsideList>()
        whenever(
            startInputMovEquipVisitTerc()
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = MovEquipVisitTercListViewModel(
            getHeader,
            getMovEquipVisitTercInsideList,
            startInputMovEquipVisitTerc
        )
        viewModel.startMov()
        assertTrue(viewModel.uiState.value.flagAccess)
        assertFalse(viewModel.uiState.value.flagDialog)
    }
}