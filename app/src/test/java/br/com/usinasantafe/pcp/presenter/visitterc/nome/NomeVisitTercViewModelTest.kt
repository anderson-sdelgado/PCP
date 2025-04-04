package br.com.usinasantafe.pcp.presenter.visitterc.nome

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.usecases.visitterc.GetNomeVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.SetIdVisitTerc
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeOcupante
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class NomeVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in GetNomeVisitTerc`() = runTest {
        val getNomeVisitTerc = mock<GetNomeVisitTerc>()
        val setIdVisitTerc = mock<SetIdVisitTerc>()
        whenever(
            getNomeVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = NomeVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.CPF_VISIT_TERC_ARGS to "123.456.789-00",
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getNomeVisitTerc,
            setIdVisitTerc
        )
        viewModel.returnNome()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> GetNomeVisitTerc -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return model if GetNomeVisitTerc execute successfully`() = runTest {
        val getNomeVisitTerc = mock<GetNomeVisitTerc>()
        val setIdVisitTerc = mock<SetIdVisitTerc>()
        whenever(
            getNomeVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(
                NomeVisitTercModel(
                    tipo = "Tipo",
                    nome = "Nome",
                    empresa = "Empresa"
                )
            )
        )
        val viewModel = NomeVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.CPF_VISIT_TERC_ARGS to "123.456.789-00",
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getNomeVisitTerc,
            setIdVisitTerc
        )
        viewModel.returnNome()
        val state = viewModel.uiState.value
        assertEquals(state.tipo, "Tipo")
        assertEquals(state.nome, "Nome")
        assertEquals(state.empresa, "Empresa")
    }

    @Test
    fun `Check return failure if have error in SetCpfVisitTerc`() = runTest {
        val getNomeVisitTerc = mock<GetNomeVisitTerc>()
        val setIdVisitTerc = mock<SetIdVisitTerc>()
        whenever(
            setIdVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val viewModel = NomeVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.CPF_VISIT_TERC_ARGS to "123.456.789-00",
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getNomeVisitTerc,
            setIdVisitTerc
        )
        viewModel.setCPF()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> SetCpfVisitTerc -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if SetCpfVisitTerc execute successfully`() = runTest {
        val getNomeVisitTerc = mock<GetNomeVisitTerc>()
        val setIdVisitTerc = mock<SetIdVisitTerc>()
        whenever(
            setIdVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = NomeVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.CPF_VISIT_TERC_ARGS to "123.456.789-00",
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getNomeVisitTerc,
            setIdVisitTerc
        )
        viewModel.setCPF()
        assertEquals(viewModel.uiState.value.flagAccess, true)
    }
}