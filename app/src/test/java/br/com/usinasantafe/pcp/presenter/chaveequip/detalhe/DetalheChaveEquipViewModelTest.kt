package br.com.usinasantafe.pcp.presenter.chaveequip.detalhe

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.CloseMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.GetDetalheMovChaveEquip
import br.com.usinasantafe.pcp.presenter.Args
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetalheChaveEquipViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getDetalheMovChaveEquip = mock<GetDetalheMovChaveEquip>()
    private val closeMovChaveEquip = mock<CloseMovChaveEquip>()

    private fun getViewModel(
        savedStateHandle: SavedStateHandle =
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
    ) = DetalheChaveEquipViewModel(
        saveStateHandle = savedStateHandle,
        getDetalheMovChaveEquip = getDetalheMovChaveEquip,
        closeMovChaveEquip = closeMovChaveEquip
    )

    @Test
    fun `recoverDetalhe - Check return failure if have error in GetDetalheMovChaveEquip`() =
        runTest {
            whenever(
                getDetalheMovChaveEquip(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetDetalheMovChaveEquip",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.recoverDetalhe()
            val state = viewModel.uiState.value
            assertEquals(
                state.flagDialog,
                true
            )
            assertEquals(
                state.failure,
                "Failure Usecase -> GetDetalheMovChaveEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverDetalhe - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getDetalheMovChaveEquip(1)
            ).thenReturn(
                Result.success(
                    DetalheChaveEquipModel(
                        dthr = "08/08/2024 12:00",
                        tipoMov = "ENTRADA",
                        equip = "100 - TRATOR",
                        colab = "19759 - ANDERSON DA SILVA DELGADO",
                        observ = "Teste Observ"
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.recoverDetalhe()
            val state = viewModel.uiState.value
            assertEquals(
                state.flagDialog,
                false
            )
            assertEquals(
                state.dthr,
                "08/08/2024 12:00"
            )
            assertEquals(
                state.tipoMov,
                "ENTRADA"
            )
            assertEquals(
                state.equip,
                "100 - TRATOR"
            )
            assertEquals(
                state.colab,
                "19759 - ANDERSON DA SILVA DELGADO"
            )
            assertEquals(
                state.observ,
                "Teste Observ"
            )
        }

    @Test
    fun `closeMov - Check return failure if have error in CloseMovChaveEquip`() =
        runTest {
            whenever(
                closeMovChaveEquip(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CloseMovChave",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.closeMov()
            val state = viewModel.uiState.value
            assertEquals(
                state.flagDialog,
                true
            )
            assertEquals(
                state.failure,
                "Failure Usecase -> CloseMovChave -> java.lang.Exception"
            )
        }

    @Test
    fun `closeMov - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                closeMovChaveEquip(1)
            ).thenReturn(
                Result.success(
                    true
                )
            )
            val viewModel = getViewModel()
            viewModel.closeMov()
            val state = viewModel.uiState.value
            assertEquals(
                state.flagDialogCheck,
                false
            )
            assertEquals(
                state.flagCloseMov,
                true
            )
        }

}