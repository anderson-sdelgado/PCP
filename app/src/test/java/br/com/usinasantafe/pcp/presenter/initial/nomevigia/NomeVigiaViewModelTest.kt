package br.com.usinasantafe.pcp.presenter.initial.nomevigia

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.initial.GetNomeVigia
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class NomeVigiaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getNomeVigia = mock<GetNomeVigia>()
    private val viewModel = NomeVigiaViewModel(getNomeVigia)

    @Test
    fun `check return failure if RecoverNomeVigia have failure`() = runTest {
        whenever(
            getNomeVigia()
        ).thenReturn(
            resultFailure(
                "GetNomeVigia",
                "-",
                Exception()
            )
        )
        viewModel.returnNomeVigia()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "NomeVigiaViewModel.returnNomeVigia -> GetNomeVigia -> java.lang.Exception"
        )
    }

    @Test
    fun `check return name if RecoverNomeVigia is success`() = runTest {
        whenever(
            getNomeVigia()
        ).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        viewModel.returnNomeVigia()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            false
        )
        assertEquals(
            viewModel.uiState.value.nomeVigia,
            "ANDERSON DA SILVA DELGADO"
        )
    }
}