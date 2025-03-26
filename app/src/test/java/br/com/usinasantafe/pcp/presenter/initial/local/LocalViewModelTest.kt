package br.com.usinasantafe.pcp.presenter.initial.local

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.usecases.config.SetIdLocalConfig
import br.com.usinasantafe.pcp.domain.usecases.initial.GetLocalList
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateLocal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LocalViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setIdLocalConfig = mock<SetIdLocalConfig>()
    private val getLocalList = mock<GetLocalList>()
    private val updateLocal = mock<UpdateLocal>()
    private val viewModel = LocalViewModel(
        getLocalList,
        setIdLocalConfig,
        updateLocal
    )

    @Test
    fun `check return failure if RecoverLocals have failure`() = runTest {
        whenever(
            getLocalList()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        viewModel.localList()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Datasource -> RecoverNomeVigia -> java.lang.Exception"
        )
    }

    @Test
    fun `check return name if RecoverNomeVigia is success`() = runTest {
        val locals = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        whenever(
            getLocalList()
        ).thenReturn(
            Result.success(
                locals
            )
        )
        viewModel.localList()
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.locals, locals)
    }
}