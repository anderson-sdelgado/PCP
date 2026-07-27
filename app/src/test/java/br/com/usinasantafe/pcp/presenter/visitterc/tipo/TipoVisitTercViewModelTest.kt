package br.com.usinasantafe.pcp.presenter.visitterc.tipo

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.SetTipoVisitTerc
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TipoVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setTipoVisitTerc = mock<SetTipoVisitTerc>()

    private fun getViewModel() = TipoVisitTercViewModel(setTipoVisitTerc)

    @Test
    fun `Check return failure if have error on set type visit terc`() = runTest {
        whenever(
            setTipoVisitTerc(
                TypeVisitTerc.TERCEIRO
            )
        ).thenReturn(
            resultFailure(
                "SetTipoVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel()

        viewModel.setTypeVisitTerc(
            TypeVisitTerc.TERCEIRO
        )
        val state = viewModel.uiState.value

        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            "TipoVisitTercViewModel.setTypeVisitTerc -> SetTipoVisitTerc -> java.lang.Exception",
            state.failure
        )
        assertEquals(
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return true if set type visit terc execute success`() = runTest {
        whenever(
            setTipoVisitTerc(TypeVisitTerc.TERCEIRO)
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel()

        viewModel.setTypeVisitTerc(TypeVisitTerc.TERCEIRO)
        val state = viewModel.uiState.value

        assertEquals(
            state.flagAccess,
            true
        )
        assertEquals(
            state.flagDialog,
            false
        )
        
    }

}
