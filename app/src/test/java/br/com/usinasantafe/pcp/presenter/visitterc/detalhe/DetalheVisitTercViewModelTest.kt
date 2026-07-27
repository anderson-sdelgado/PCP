package br.com.usinasantafe.pcp.presenter.visitterc.detalhe

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.errors.resultFailure // Import necessário
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.CloseMovVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetDetalheVisitTerc
import br.com.usinasantafe.pcp.presenter.Args
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetalheVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Mocks como propriedades da classe (já estava padronizado)
    private val getDetalheVisitTerc = mock<GetDetalheVisitTerc>()
    private val closeMovVisitTerc = mock<CloseMovVisitTerc>()

    // Helper function para criar ViewModel (já estava padronizado)
    private fun getViewModel(
        savedStateHandle: SavedStateHandle
    ) = DetalheVisitTercViewModel(
        savedStateHandle,
        getDetalheVisitTerc,
        closeMovVisitTerc
    )

    @Test
    fun `Check return failure if have error in recoverDetalhe`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            getDetalheVisitTerc(
                id = 1
            )
        ).thenReturn(
            resultFailure( // Usando resultFailure (já estava padronizado)
                "GetDetalheVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel( // Usando getViewModel (já estava padronizado)
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem já estava padronizado
            "DetalheVisitTercViewModel.recoverDetalhe -> GetDetalheVisitTerc -> java.lang.Exception",
            state.failure
        )
    }

    @Test
    fun `Check return model if recoverDetalhe execute correctly`() = runTest {
        // Arrange
        val expectedModel = DetalheVisitTercModel(
            dthr = "08/08/2024 12:00",
            tipoMov = "ENTRADA",
            veiculo = "GOL",
            placa = "AAA-0000",
            tipoVisitTerc = "VISITANTE",
            motorista = "19759 - ANDERSON DA SILVA DELGADO",
            passageiro = "19035 - JOSE DONIZETE; 18017 - RONALDO;",
            destino = "Teste Destino",
            observ = "Teste Observ"
        )
        // PADRONIZADO: whenever multi-linha (aplicado aqui)
        whenever(
            getDetalheVisitTerc(1)
        ).thenReturn(
            Result.success(expectedModel)
        )
        val viewModel = getViewModel( // Usando getViewModel (já estava padronizado)
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // expected, actual (string)
            expectedModel.dthr,
            state.dthr
        )
        assertEquals( // expected, actual (string)
            expectedModel.tipoMov,
            state.tipoMov
        )
        assertEquals( // expected, actual (string)
            expectedModel.veiculo,
            state.veiculo
        )
        assertEquals( // expected, actual (string)
            expectedModel.placa,
            state.placa
        )
        assertEquals( // expected, actual (string)
            expectedModel.tipoVisitTerc,
            state.tipoVisitTerc
        )
        assertEquals( // expected, actual (string)
            expectedModel.motorista,
            state.motorista
        )
        assertEquals( // expected, actual (string)
            expectedModel.passageiro,
            state.passageiro
        )
        assertEquals( // expected, actual (string)
            expectedModel.destino,
            state.destino
        )
        assertEquals( // expected, actual (string)
            expectedModel.observ,
            state.observ
        )
        assertEquals( // actual, expected (boolean) - Verificação de ausência de erro
            state.flagDialog,
            false
        )
        
    }

    @Test
    fun `Check return failure if have error in CloseMovVisitTercOpen`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            closeMovVisitTerc(
                id = 1
            )
        ).thenReturn(
            resultFailure( // Usando resultFailure (já estava padronizado)
                "CloseMovVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel( // Usando getViewModel (já estava padronizado)
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.closeMov()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagDialog,
            true
        )
        assertEquals( // expected, actual (string multi-linha) - Formato da mensagem já estava padronizado
            "DetalheVisitTercViewModel.closeMov -> CloseMovVisitTerc -> java.lang.Exception",
            state.failure
        )
    }

    @Test
    fun `Check return true if CloseMovVisitTercOpen execute correctly`() = runTest {
        // Arrange
        // PADRONIZADO: whenever multi-linha
        whenever(
            closeMovVisitTerc(1)
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel( // Usando getViewModel (já estava padronizado)
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            )
        )

        // Act
        viewModel.closeMov()
        val state = viewModel.uiState.value

        // Assert
        // PADRONIZADO: Asserts individuais multi-linha com comentários
        assertEquals( // actual, expected (boolean)
            state.flagCloseMov,
            true
        )
        assertEquals( // actual, expected (boolean) - Verificação de ausência de erro
            state.flagDialog,
            false
        )
        
    }
}
