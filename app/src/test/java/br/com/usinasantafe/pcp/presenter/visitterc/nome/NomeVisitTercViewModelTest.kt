package br.com.usinasantafe.pcp.presenter.visitterc.nome

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetNomeVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.SetIdVisitTerc
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeOcupante
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

    // Mocks como propriedades da classe
    private val getNomeVisitTerc = mock<GetNomeVisitTerc>()
    private val setIdVisitTerc = mock<SetIdVisitTerc>()

    // Helper function para criar ViewModel
    private fun getViewModel(
        savedStateHandle: SavedStateHandle
    ) = NomeVisitTercViewModel(
        savedStateHandle,
        getNomeVisitTerc,
        setIdVisitTerc
    )

    @Test
    fun `Check return failure if have error in GetNomeVisitTerc`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            getNomeVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "GetNomeVisitTerc",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.CPF_VISIT_TERC_ARGS to "123.456.789-00",
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        // Act
        viewModel.returnNome()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem padronizado
            "NomeVisitTercViewModel.returnNome -> GetNomeVisitTerc -> java.lang.Exception",
            state.failure
        )
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.tipo,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.nome,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.empresa,
            ""
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return model if GetNomeVisitTerc execute successfully`() = runTest {
        // Arrange
        val expectedModel = NomeVisitTercModel( // PADRONIZADO: Nome da variável mais descritivo
            tipo = "Tipo",
            nome = "Nome",
            empresa = "Empresa"
        )
        // PADRONIZADO: whenever multi-linha
        whenever(
            getNomeVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(expectedModel)
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.CPF_VISIT_TERC_ARGS to "123.456.789-00",
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        // Act
        viewModel.returnNome()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // expected, actual (string)
            expectedModel.tipo,
            state.tipo
        )
        assertEquals( // expected, actual (string)
            expectedModel.nome,
            state.nome
        )
        assertEquals( // expected, actual (string)
            expectedModel.empresa,
            state.empresa
        )
        assertEquals( // actual, expected (boolean) - Verificação de ausência de erro
            state.flagDialog,
            false
        )
        
        // PADRONIZADO: Verificar outros estados relevantes
        assertEquals( // actual, expected (boolean) - Estado inicial
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return failure if have error in SetIdVisitTerc`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            setIdVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "SetIdVisitTerc", // Nome do use case correto
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.CPF_VISIT_TERC_ARGS to "123.456.789-00",
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        // Act
        viewModel.setCPF()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem padronizado
            "NomeVisitTercViewModel.setCPF -> SetIdVisitTerc -> java.lang.Exception", // Nome do use case correto
            state.failure
        )
        // PADRONIZADO: Verificar outros estados relevantes (returnNome não foi chamado/sucedido)
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.tipo,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.nome,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial/erro
            state.empresa,
            ""
        )
        assertEquals( // actual, expected (boolean) - Estado inicial/erro
            state.flagAccess,
            false
        )
    }

    @Test
    fun `Check return true if SetIdVisitTerc execute successfully`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
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
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.CPF_VISIT_TERC_ARGS to "123.456.789-00",
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )

        // Act
        viewModel.setCPF()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagAccess,
            true // Acesso/sucesso ocorreu
        )
        assertEquals( // actual, expected (boolean) - Verificação de ausência de erro
            state.flagDialog,
            false
        )
        
        // PADRONIZADO: Verificar outros estados relevantes (returnNome não foi chamado/sucedido)
        assertEquals( // actual, expected (string) - Estado inicial
            state.tipo,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial
            state.nome,
            ""
        )
        assertEquals( // actual, expected (string) - Estado inicial
            state.empresa,
            ""
        )
    }
}
