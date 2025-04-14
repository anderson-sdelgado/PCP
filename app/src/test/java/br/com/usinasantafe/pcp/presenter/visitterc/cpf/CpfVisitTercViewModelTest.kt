package br.com.usinasantafe.pcp.presenter.visitterc.cpf

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.errors.resultFailure // <<<--- IMPORT ADICIONADO
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateTerceiro
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateVisitante
import br.com.usinasantafe.pcp.domain.usecases.visitterc.CheckCpfVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.GetCpfVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.GetTitleCpfVisitTerc
import br.com.usinasantafe.pcp.presenter.Args
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeButton
import br.com.usinasantafe.pcp.utils.TypeOcupante
import br.com.usinasantafe.pcp.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CpfVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // PADRONIZADO: Mocks como propriedades da classe
    private val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
    private val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
    private val getCpfVisitTerc = mock<GetCpfVisitTerc>()
    private val updateTerceiro = mock<UpdateTerceiro>()
    private val updateVisitante = mock<UpdateVisitante>()

    // PADRONIZADO: Helper function para criar ViewModel
    private fun getViewModel(
        savedStateHandle: SavedStateHandle
    ) = CpfVisitTercViewModel(
        savedStateHandle,
        getTitleCpfVisitTerc,
        checkCpfVisitTerc,
        getCpfVisitTerc,
        updateTerceiro,
        updateVisitante
    )
    private val sizeAll = 7f // Mantido para testes de update

    @Test
    fun `Check return failure if fields is empty`() {
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setTextField("", TypeButton.OK)
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas e ordem invertida (state, expected)
        assertEquals( // Convertido de assertTrue
            state.flagDialog,
            true
        )
        assertEquals( // Convertido de assertTrue
            state.flagFailure,
            true
        )
        assertEquals(
            Errors.FIELDEMPTY,
            state.errors
        )
        
    }

    @Test
    fun `Check adjustment of cpf in typing`() {
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setTextField("1", TypeButton.NUMERIC)
        viewModel.setTextField("2", TypeButton.NUMERIC)
        viewModel.setTextField("3", TypeButton.NUMERIC)
        viewModel.setTextField("4", TypeButton.NUMERIC)
        viewModel.setTextField("5", TypeButton.NUMERIC)
        viewModel.setTextField("6", TypeButton.NUMERIC)
        viewModel.setTextField("7", TypeButton.NUMERIC)
        viewModel.setTextField("8", TypeButton.NUMERIC)
        viewModel.setTextField("9", TypeButton.NUMERIC)
        viewModel.setTextField("0", TypeButton.NUMERIC)
        viewModel.setTextField("0", TypeButton.NUMERIC)
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas (ordem mantida para não booleanos/null)
        assertEquals(
            "123.456.789-00",
            state.cpf
        )
    }

    @Test
    fun `Check return failure if have error in CleanTerceiro`() = runTest {
        val qtdBefore = 0f
        // PADRONIZADO: Mocks não são mais criados aqui
        // PADRONIZADO: Mock retorna a falha originada no use case
        whenever(
            updateTerceiro(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "CleanTerceiro -> java.lang.NullPointerException", // Falha do use case
                    msgProgress = "CleanTerceiro -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        // FORMATADO: assertEquals em multi-linhas (ordem mantida para não booleanos/null)
        assertEquals(
            ((qtdBefore * 3) + 2).toInt(),
            result.count()
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            ),
            result[0]
        )
        // PADRONIZADO: Asserção no estado emitido espera a string completa (ViewModel + UseCase)
        assertEquals(
            CpfVisitTercState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "CpfVisitTercViewModel.updateAllDatabase -> CleanTerceiro -> java.lang.NullPointerException",
                msgProgress = "CpfVisitTercViewModel.updateAllDatabase -> CleanTerceiro -> java.lang.NullPointerException",
                currentProgress = 1f,
            ),
            result[1]
        )
        // PADRONIZADO: Asserção no estado final do ViewModel
        val finalState = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas e ordem invertida (state, expected)
        assertEquals( // Convertido de assertTrue
            finalState.flagDialog,
            true
        )
        assertEquals( // Convertido de assertTrue
            finalState.flagFailure,
            true
        )
        assertEquals( // Ordem mantida para não booleanos/null
            Errors.UPDATE,
            finalState.errors
        )
        assertEquals( // Ordem mantida para não booleanos/null
            "CpfVisitTercViewModel.updateAllDatabase -> CleanTerceiro -> java.lang.NullPointerException",
            finalState.failure
        )
        assertEquals( // Verifica msgProgress final também (Ordem mantida)
            "CpfVisitTercViewModel.updateAllDatabase -> CleanTerceiro -> java.lang.NullPointerException",
            finalState.msgProgress
        )
    }

    @Test
    fun `Check return failure if have error in CleanVisitante`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
        whenever(
            updateTerceiro(
                sizeAll = sizeAll,
                count = 1f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = percentage(1f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = percentage(2f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_terceiro",
                    currentProgress = percentage(3f, sizeAll)
                ),
            )
        )
        // PADRONIZADO: Mock retorna a falha originada no use case
        whenever(
            updateVisitante(
                sizeAll = sizeAll,
                count = 2f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = percentage(4f, sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "CleanVisitante -> java.lang.NullPointerException", // Falha do use case
                    msgProgress = "CleanVisitante -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        // FORMATADO: assertEquals em multi-linhas (ordem mantida para não booleanos/null)
        assertEquals(
            5,
            result.count()
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(1f, 7f)
            ),
            result[0]
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = percentage(2f, 7f)
            ),
            result[1]
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = percentage(3f, 7f)
            ),
            result[2]
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(4f, 7f)
            ),
            result[3]
        )
        // PADRONIZADO: Asserção no estado emitido espera a string completa (ViewModel + UseCase)
        assertEquals(
            CpfVisitTercState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "CpfVisitTercViewModel.updateAllDatabase -> CleanVisitante -> java.lang.NullPointerException",
                msgProgress = "CpfVisitTercViewModel.updateAllDatabase -> CleanVisitante -> java.lang.NullPointerException",
                currentProgress = 1f,
            ),
            result[4]
        )
        // PADRONIZADO: Asserção no estado final do ViewModel
        val finalState = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas e ordem invertida (state, expected)
        assertEquals( // Convertido de assertTrue
            finalState.flagDialog,
            true
        )
        assertEquals( // Convertido de assertTrue
            finalState.flagFailure,
            true
        )
        assertEquals( // Ordem mantida para não booleanos/null
            Errors.UPDATE,
            finalState.errors
        )
        assertEquals( // Ordem mantida para não booleanos/null
            "CpfVisitTercViewModel.updateAllDatabase -> CleanVisitante -> java.lang.NullPointerException",
            finalState.failure
        )
        assertEquals( // Verifica msgProgress final também (Ordem mantida)
            "CpfVisitTercViewModel.updateAllDatabase -> CleanVisitante -> java.lang.NullPointerException",
            finalState.msgProgress
        )
    }

    @Test
    fun `Check return success if UpdateAllTable execute successfully`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
        whenever(
            updateTerceiro(
                sizeAll = sizeAll,
                count = 1f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = percentage(1f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = percentage(2f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_terceiro",
                    currentProgress = percentage(3f, sizeAll)
                ),
            )
        )
        whenever(
            updateVisitante(
                sizeAll = sizeAll,
                count = 2f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = percentage(4f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = percentage(5f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_visitante",
                    currentProgress = percentage(6f, sizeAll)
                ),
            )
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        // FORMATADO: assertEquals em multi-linhas (ordem mantida para não booleanos/null)
        assertEquals(
            7,
            result.count()
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(1f, 7f)
            ),
            result[0]
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = percentage(2f, 7f)
            ),
            result[1]
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = percentage(3f, 7f)
            ),
            result[2]
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(4f, 7f)
            ),
            result[3]
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = percentage(5f, 7f)
            ),
            result[4]
        )
        assertEquals(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_visitante",
                currentProgress = percentage(6f, 7f)
            ),
            result[5]
        )
        assertEquals(
            CpfVisitTercState(
                flagDialog = true, // Dialogo de sucesso
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            ),
            result[6]
        )
        val finalState = viewModel.uiState.value
        assertEquals( // Convertido de assertTrue
            finalState.flagDialog, // Dialogo de sucesso
            true
        )
        assertEquals( // Convertido de assertFalse
            finalState.flagFailure,
            false
        )
        assertEquals( // Ordem mantida para não booleanos/null
            "Atualização de dados realizado com sucesso!",
            finalState.msgProgress
        )
    }

    @Test
    fun `Check return failure if have error in CheckCpfVisitTerc`() = runTest {
        whenever(
            checkCpfVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "CheckCpfVisitTerc",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setTextField(
            "123.456.789-00",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "",
            TypeButton.OK
        )
        val state = viewModel.uiState.value
        assertEquals(
            state.flagDialog,
            true
        )
        assertEquals(
            state.flagFailure,
            true
        )
        assertEquals(
            Errors.EXCEPTION,
            state.errors
        )
        assertEquals(
            "CpfVisitTercViewModel.checkCpf -> CheckCpfVisitTerc -> java.lang.Exception",
            state.failure
        )
    }

    @Test
    fun `Check return true if cpf is correct`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
        whenever(
            checkCpfVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setTextField("123.456.789-00", TypeButton.NUMERIC)
        viewModel.setTextField("", TypeButton.OK)
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas e ordem invertida (state, expected)
        assertEquals( // Convertido de assertTrue
            state.flagAccess,
            true
        )
        // PADRONIZADO: Verificar ausência de erro
        assertEquals( // Convertido de assertFalse
            state.flagDialog,
            false
        )
        assertEquals( // Convertido de assertFalse
            state.flagFailure,
            false
        )

        
    }

    @Test
    fun `Check return false if cpf is incorrect`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
        whenever(
            checkCpfVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(false) // Use case indica CPF inválido/não encontrado
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setTextField("123.456.789-00", TypeButton.NUMERIC)
        viewModel.setTextField("", TypeButton.OK)
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas e ordem invertida (state, expected)
        assertEquals( // Convertido de assertFalse
            state.flagAccess,
            false
        )
        assertEquals( // Convertido de assertTrue
            state.flagDialog,
            true
        )
        assertEquals( // Convertido de assertTrue
            state.flagFailure,
            true
        )

    }

    @Test
    fun `Check return failure if have error in GetCpfVisitTerc`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
        // PADRONIZADO: Usando resultFailure
        whenever(
            getCpfVisitTerc(
                id = 1
            )
        ).thenReturn(
            resultFailure(
                "GetCpfVisitTerc",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 1,
                )
            )
        )
        viewModel.getCpf()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas e ordem invertida (state, expected)
        assertEquals( // Convertido de assertTrue
            state.flagDialog,
            true
        )
        assertEquals( // Convertido de assertTrue
            state.flagFailure,
            true
        )
        assertEquals( // Ordem mantida para não booleanos/null
            Errors.EXCEPTION,
            state.errors
        )
        // PADRONIZADO: Formato da mensagem de erro (Ordem mantida)
        assertEquals(
            "CpfVisitTercViewModel.getCpf -> GetCpfVisitTerc -> java.lang.Exception",
            state.failure
        )
    }

    @Test
    fun `Check return cpf if GetCpfVisitTerc execute successfully`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
        whenever(
            getCpfVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("123.456.789-00")
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 1,
                )
            )
        )
        viewModel.getCpf()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas e ordem invertida (state, expected)
        assertEquals( // Ordem mantida para não booleanos/null
            "123.456.789-00",
            state.cpf
        )
        assertEquals( // Convertido de assertFalse
            state.checkGetCpf, // Flag interna do ViewModel
            false
        )
        // PADRONIZADO: Verificar ausência de erro
        assertEquals( // Convertido de assertFalse
            state.flagDialog,
            false
        )
        assertEquals( // Convertido de assertFalse
            state.flagFailure,
            false
        )

        
    }

    @Test
    fun `Check return failure if have error in GetTitleCpfVisitTerc`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
        // PADRONIZADO: Usando resultFailure
        whenever(
            getTitleCpfVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            resultFailure(
                "GetTitleCpfVisitTerc",
                "-",
                Exception()
            )
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.recoverTitle()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas e ordem invertida (state, expected)
        assertEquals( // Convertido de assertTrue
            state.flagDialog,
            true
        )
        assertEquals( // Convertido de assertTrue
            state.flagFailure,
            true
        )
        assertEquals( // Ordem mantida para não booleanos/null
            Errors.EXCEPTION,
            state.errors
        )
        // PADRONIZADO: Formato da mensagem de erro (Ordem mantida)
        assertEquals(
            "CpfVisitTercViewModel.recoverTitle -> GetTitleCpfVisitTerc -> java.lang.Exception",
            state.failure
        )
    }

    @Test
    fun `Check return title if GetTitleCpfVisitTerc execute successfully`() = runTest {
        // PADRONIZADO: Mocks não são mais criados aqui
        whenever(
            getTitleCpfVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success("CPF VISITANTE")
        )
        // PADRONIZADO: Usando getViewModel
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.recoverTitle()
        val state = viewModel.uiState.value
        // FORMATADO: assertEquals em multi-linhas e ordem invertida (state, expected)
        assertEquals( // Ordem mantida para não booleanos/null
            "CPF VISITANTE",
            state.title
        )
        // PADRONIZADO: Verificar ausência de erro
        assertEquals( // Convertido de assertFalse
            state.flagDialog,
            false
        )
        assertEquals( // Convertido de assertFalse
            state.flagFailure,
            false
        )
    }
}
