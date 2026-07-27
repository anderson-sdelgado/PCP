package br.com.usinasantafe.pcp.presenter.initial.matricvigia

import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcp.domain.usecases.config.SetMatricVigiaConfig
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableColab
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.TypeButton
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
class MatricVigiaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkMatricColab = mock<CheckMatricColab>()
    private val setMatricVigiaConfig = mock<SetMatricVigiaConfig>()
    private val updateTableColab = mock<UpdateTableColab>()
    private val viewModel = MatricVigiaViewModel(
        checkMatricColab,
        setMatricVigiaConfig,
        updateTableColab
    )

    @Test
    fun `Check add char in matricVigia`() {
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.matricVigia,
            "19759"
        )
    }

    @Test
    fun `Check remover and add char in matricVigia`() {
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.matricVigia,
            "191"
        )
    }

    @Test
    fun `Check view msg if field is empty`() {
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.errors,
            Errors.FIELD_EMPTY
        )
    }

    @Test
    fun `Check return failure if have error in CheckMatricColab`() = runTest {
        whenever(
            checkMatricColab("19759")
        ).thenReturn(
            resultFailure(
                "CheckMatricColab",
                "-",
                Exception()
            )
        )
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.errors,
            Errors.EXCEPTION
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "MatricVigiaViewModel.setMatricVigia -> CheckMatricColab -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have error in SetMatricVigiaConfig`() = runTest {
        whenever(
            checkMatricColab("19759")
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            setMatricVigiaConfig("19759")
        ).thenReturn(
            resultFailure(
                "SetMatricVigiaConfig",
                "-",
                Exception()
            )
        )
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.errors,
            Errors.EXCEPTION
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "MatricVigiaViewModel.setMatricVigia -> SetMatricVigiaConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return false if matric is invalid`() = runTest {
        whenever(
            checkMatricColab("19759")
        ).thenReturn(
            Result.success(true)
        )
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            true
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            false
        )
    }

    @Test
    fun `Check return true if matric is valid`() = runTest {
        whenever(
            checkMatricColab("19759")
        ).thenReturn(
            Result.success(false)
        )
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.flagAccess,
            false
        )
        assertEquals(
            viewModel.uiState.value.flagFailure,
            true
        )
    }

    @Test
    fun `Check return failure datasource if have error in usecase CleanColab is datasource`() =
        runTest {
            whenever(
                updateTableColab(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanColab -> java.lang.NullPointerException",
                        msgProgress = "CleanColab -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                MatricVigiaState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                MatricVigiaState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "MatricVigiaViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException",
                    msgProgress = "MatricVigiaViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase in setTextField if have error in usecase CleanColab`() =
        runTest {
            whenever(
                updateTableColab(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanColab -> java.lang.NullPointerException",
                        msgProgress = "CleanColab -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                MatricVigiaState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                MatricVigiaState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "MatricVigiaViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException",
                    msgProgress = "MatricVigiaViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField(
                "ATUALIZAR DADOS",
                TypeButton.UPDATE
            )
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "MatricVigiaViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return success in updateAllDatabase if all update run correctly`() = runTest {
        whenever(
            updateTableColab(
                count = 1f,
                sizeAll = 4f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 4f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = percentage(2f, 4f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = percentage(3f, 4f)
                ),
            )
        )
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(
            result.count(),
            4
        )
        assertEquals(
            result[0],
            MatricVigiaState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = percentage(1f, 4f)
            )
        )
        assertEquals(
            result[1],
            MatricVigiaState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = percentage(2f, 4f),
            )
        )
        assertEquals(
            result[2],
            MatricVigiaState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_colab",
                currentProgress = percentage(3f, 4f),
            )
        )
        assertEquals(
            result[3],
            MatricVigiaState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `Check return success if setTextField update is success`() = runTest {
        whenever(
            updateTableColab(
                count = 1f,
                sizeAll = 4f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 4f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = percentage(2f, 4f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = percentage(3f, 4f)
                ),
            )
        )
        viewModel.setTextField("ATUALIZAR DADOS", TypeButton.UPDATE)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Atualização de dados realizado com sucesso!"
        )
    }

}