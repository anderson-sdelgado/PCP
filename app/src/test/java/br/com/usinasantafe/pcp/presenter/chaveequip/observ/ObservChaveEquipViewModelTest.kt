package br.com.usinasantafe.pcp.presenter.chaveequip.observ

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.GetObservMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.SaveMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.SetObservMovChaveEquip
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ObservChaveEquipViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setObservMovChaveEquip = mock<SetObservMovChaveEquip>()
    private val saveMovChaveEquip = mock<SaveMovChaveEquip>()
    private val getObservMovChaveEquip = mock<GetObservMovChaveEquip>()

    private fun getViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                Args.TYPE_MOV_ARGS  to TypeMovKey.RECEIPT.ordinal,
                Args.ID_ARGS to 0
            )
        )
    ) = ObservChaveEquipViewModel(
        savedStateHandle,
        setObservMovChaveEquip,
        saveMovChaveEquip,
        getObservMovChaveEquip
    )

    @Test
    fun `setObserv - Check return failure if have error in SetObservMovChave - RECEIPT - ADD`() =
        runTest {
            whenever(
                setObservMovChaveEquip(
                    flowApp = FlowApp.ADD,
                    id = 0,
                    observ = null
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val viewModel = getViewModel()
            viewModel.setObserv()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> ISetObservMovChaveEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `setObserv - Check return failure if have error in SaveMovChave - RECEIPT - ADD`() =
        runTest {
            whenever(
                setObservMovChaveEquip(
                    flowApp = FlowApp.ADD,
                    id = 0,
                    observ = null
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveMovChaveEquip(
                    typeMov = TypeMovKey.RECEIPT,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val viewModel = getViewModel()
            viewModel.setObserv()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> ISaveMovChave -> java.lang.Exception"
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully - RECEIPT - ADD`() =
        runTest {
            whenever(
                setObservMovChaveEquip(
                    flowApp = FlowApp.ADD,
                    id = 0,
                    observ = null
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveMovChaveEquip(
                    typeMov = TypeMovKey.RECEIPT,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getViewModel()
            viewModel.setObserv()
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
    fun `getObserv - Check return failure if have error in GetObservMovChaveEquip`() =
        runTest {
            whenever(
                getObservMovChaveEquip(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARGS  to FlowApp.CHANGE.ordinal,
                        Args.TYPE_MOV_ARGS  to TypeMovKey.RECEIPT.ordinal,
                        Args.ID_ARGS to 1
                    )
                )
            )
            viewModel.getObserv()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> IGetObservMovChaveEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `getObserv - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getObservMovChaveEquip(
                    id = 1
                )
            ).thenReturn(
                Result.success("OBSERV")
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARGS  to FlowApp.CHANGE.ordinal,
                        Args.TYPE_MOV_ARGS  to TypeMovKey.RECEIPT.ordinal,
                        Args.ID_ARGS to 1
                    )
                )
            )
            viewModel.getObserv()
            assertEquals(
                viewModel.uiState.value.flagGetObserv,
                false
            )
            assertEquals(
                viewModel.uiState.value.observ,
                "OBSERV"
            )
        }
}