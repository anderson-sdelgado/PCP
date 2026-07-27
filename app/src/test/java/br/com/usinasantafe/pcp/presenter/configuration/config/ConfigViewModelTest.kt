package br.com.usinasantafe.pcp.presenter.configuration.config
import br.com.usinasantafe.pcp.MainCoroutineRule
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.pcp.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.pcp.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.pcp.domain.usecases.config.SetCheckUpdateAllTable
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableChave
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableColab
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableEquip
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableFluxo
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableLocal
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableLocalTrab
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableRLocalFluxo
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableTerceiro
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableVisitante
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.FlagUpdate
import br.com.usinasantafe.pcp.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ConfigViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getConfigInternal = mock<GetConfigInternal>()
    private val sendDataConfig = mock<SendDataConfig>()
    private val saveDataConfig = mock<SaveDataConfig>()
    private val updateTableChave = mock<UpdateTableChave>()
    private val updateTableColab = mock<UpdateTableColab>()
    private val updateTableEquip = mock<UpdateTableEquip>()
    private val updateTableFluxo = mock<UpdateTableFluxo>()
    private val updateTableLocal = mock<UpdateTableLocal>()
    private val updateTableLocalTrab = mock<UpdateTableLocalTrab>()
    private val updateTableRLocalFluxo = mock<UpdateTableRLocalFluxo>()
    private val updateTableTerceiro = mock<UpdateTableTerceiro>()
    private val updateTableVisitante = mock<UpdateTableVisitante>()
    private val setCheckUpdateAllTable = mock<SetCheckUpdateAllTable>()
    private val sizeAll = 28f
    private var contWhenever = 0f
    private var contResult = 0f
    private var contUpdate = 0f

    private fun getViewModel() = ConfigViewModel(
        getConfigInternal = getConfigInternal,
        sendDataConfig = sendDataConfig,
        saveDataConfig = saveDataConfig,
        updateChave = updateTableChave,
        updateColab = updateTableColab,
        updateEquip = updateTableEquip,
        updateFluxo = updateTableFluxo,
        updateLocal = updateTableLocal,
        updateLocalTrab = updateTableLocalTrab,
        updateRLocalFluxo = updateTableRLocalFluxo,
        updateTerceiro = updateTableTerceiro,
        updateVisitante = updateTableVisitante,
        setCheckUpdateAllTable = setCheckUpdateAllTable,
    )

    @Test
    fun `Check return null if don't have Config table internal`() = runTest {
        whenever(
            getConfigInternal()
        ).thenReturn(
            null
        )
        val viewModel = getViewModel()
        viewModel.returnDataConfig()
        assertEquals(
            viewModel.uiState.value.number,
            ""
        )
        assertEquals(
            viewModel.uiState.value.password,
            ""
        )
    }

    @Test
    fun `Check return data if have Config table internal`() = runTest {
        val configModel = ConfigModel(
            number = "16997417840",
            password = "12345"
        )
        whenever(
            getConfigInternal()
        ).thenReturn(
            Result.success(configModel)
        )
        val viewModel = getViewModel()
        viewModel.returnDataConfig()
        assertEquals(
            viewModel.uiState.value.number,
            "16997417840"
        )
        assertEquals(
            viewModel.uiState.value.password,
            "12345"
        )
    }

    @Test
    fun `Check return msg when field empty`() = runTest {
        val viewModel = getViewModel()
        viewModel.saveTokenAndUpdateAllDatabase()
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
    fun `check return failure usecase when field number type is not type number`() = runTest {
        whenever(
            sendDataConfig(
                number = "16dfda",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(
            resultFailure(
                "ISendDataConfig",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.onNumberChanged("16dfda")
        viewModel.onPasswordChanged("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(
            result.count(),
            2
        )
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = percentage(1f, 3f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.TOKEN,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.token -> ISendDataConfig -> java.lang.Exception",
                msgProgress = "ConfigViewModel.token -> ISendDataConfig -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "ConfigViewModel.token -> ISendDataConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure datasource if have errors in Datasource`() = runTest {
        whenever(
            sendDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(
            resultFailure(
                "ISendDataConfig",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.onNumberChanged("16997417840")
        viewModel.onPasswordChanged("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = percentage(1f, 3f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.TOKEN,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.token -> ISendDataConfig -> java.lang.Exception",
                msgProgress = "ConfigViewModel.token -> ISendDataConfig -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "ConfigViewModel.token -> ISendDataConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure usecase in save if have errors in Save data Usecase`() = runTest {
        whenever(
            sendDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(
            Result.success(1)
        )
        whenever(
            saveDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(
            resultFailure(
                "ISaveDataConfig",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel()
        viewModel.onNumberChanged("16997417840")
        viewModel.onPasswordChanged("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(
            result.count(),
            3
        )
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = percentage(1f, 3f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados de Token",
                currentProgress = percentage(2f, 3f),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.TOKEN,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.token -> ISaveDataConfig -> java.lang.Exception",
                msgProgress = "ConfigViewModel.token -> ISaveDataConfig -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "ConfigViewModel.token -> ISaveDataConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return finally sent and save Config`() = runTest {
        whenever(
            sendDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(
            Result.success(1)
        )
        whenever(
            saveDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel()
        viewModel.onNumberChanged("16997417840")
        viewModel.onPasswordChanged("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = percentage(1f, 3f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados de Token",
                currentProgress = percentage(2f, 3f),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Ajuste iniciais finalizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateChave`() = runTest {
        whenever(
            updateTableChave(
                sizeAll = sizeAll,
                count = 1f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = percentage(1f, sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "CleanChave -> java.lang.NullPointerException",
                    msgProgress = "CleanChave -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_chave",
                currentProgress = percentage(1f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.updateAllDatabase -> CleanChave -> java.lang.NullPointerException",
                msgProgress = "ConfigViewModel.updateAllDatabase -> CleanChave -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateColab`() = runTest {
        val qtdBefore = 1f
        wheneverSuccessChave()
        whenever(
            updateTableColab(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "CleanColab -> java.lang.NullPointerException",
                    msgProgress = "CleanColab -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(
            result.count(),
            ((qtdBefore * 3) + 2).toInt()
        )
        checkResultUpdateChave(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException",
                msgProgress = "ConfigViewModel.updateAllDatabase -> CleanColab -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateEquip`() = runTest {
        val qtdBefore = 2f
        wheneverSuccessChave()
        wheneverSuccessColab()
        whenever(
            updateTableEquip(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "CleanEquip -> java.lang.NullPointerException",
                    msgProgress = "CleanEquip -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(
            result.count(),
            ((qtdBefore * 3) + 2).toInt()
        )
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException",
                msgProgress = "ConfigViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateFluxo`() = runTest {
        val qtdBefore = 3f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        whenever(
            updateTableFluxo(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "CleanFluxo -> java.lang.NullPointerException",
                    msgProgress = "CleanFluxo -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(
            result.count(),
            ((qtdBefore * 3) + 2).toInt()
        )
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_fluxo",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.updateAllDatabase -> CleanFluxo -> java.lang.NullPointerException",
                msgProgress = "ConfigViewModel.updateAllDatabase -> CleanFluxo -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateLocal`() = runTest {
        val qtdBefore = 4f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        whenever(
            updateTableLocal(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "CleanLocal -> java.lang.NullPointerException",
                    msgProgress = "CleanLocal -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(
            result.count(),
            ((qtdBefore * 3) + 2).toInt()
        )
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.updateAllDatabase -> CleanLocal -> java.lang.NullPointerException",
                msgProgress = "ConfigViewModel.updateAllDatabase -> CleanLocal -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateLocalTrab`() = runTest {
        val qtdBefore = 5f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        whenever(
            updateTableLocalTrab(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local_trab",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "CleanLocalTrab -> java.lang.NullPointerException",
                    msgProgress = "CleanLocalTrab -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local_trab",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.updateAllDatabase -> CleanLocalTrab -> java.lang.NullPointerException",
                msgProgress = "ConfigViewModel.updateAllDatabase -> CleanLocalTrab -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateRLocalFluxo`() = runTest {
        val qtdBefore = 6f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        whenever(
            updateTableRLocalFluxo(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_local_fluxo",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "CleanRLocalFluxo -> java.lang.NullPointerException",
                    msgProgress = "CleanRLocalFluxo -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateLocalTrab(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_r_local_fluxo",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.updateAllDatabase -> CleanRLocalFluxo -> java.lang.NullPointerException",
                msgProgress = "ConfigViewModel.updateAllDatabase -> CleanRLocalFluxo -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateTerceiro`() = runTest {
        val qtdBefore = 7f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        wheneverSuccessRLocalFluxo()
        whenever(
            updateTableTerceiro(
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
                    failure = "CleanTerceiro -> java.lang.NullPointerException",
                    msgProgress = "CleanTerceiro -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateLocalTrab(result)
        checkResultUpdateRLocalFluxo(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.updateAllDatabase -> CleanTerceiro -> java.lang.NullPointerException",
                msgProgress = "ConfigViewModel.updateAllDatabase -> CleanTerceiro -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateVisitante`() = runTest {
        val qtdBefore = 8f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        wheneverSuccessRLocalFluxo()
        wheneverSuccessTerceiro()
        whenever(
            updateTableVisitante(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "CleanVisitante -> java.lang.NullPointerException",
                    msgProgress = "CleanVisitante -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateLocalTrab(result)
        checkResultUpdateRLocalFluxo(result)
        checkResultUpdateTerceiro(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.updateAllDatabase -> CleanVisitante -> java.lang.NullPointerException",
                msgProgress = "ConfigViewModel.updateAllDatabase -> CleanVisitante -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure datasource if have error in datasource SetCheckUpdateAllTable`() = runTest {
        val qtdBefore = 9f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        wheneverSuccessRLocalFluxo()
        wheneverSuccessTerceiro()
        wheneverSuccessVisitante()
        whenever(
            setCheckUpdateAllTable(FlagUpdate.UPDATED)
        ).thenReturn(
            resultFailure(
                "ISetCheckUpdateAllTable",
                "-",
                Exception()
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 1).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateLocalTrab(result)
        checkResultUpdateRLocalFluxo(result)
        checkResultUpdateTerceiro(result)
        checkResultUpdateVisitante(result)
        assertEquals(
            result[27],
            ConfigState(
                errors = Errors.EXCEPTION,
                flagDialog = true,
                flagFailure = true,
                failure = "ConfigViewModel.updateAllDatabase -> ISetCheckUpdateAllTable -> java.lang.Exception",
            )
        )
    }

    @Test
    fun `check return success if all update run correctly`() = runTest {
        val qtdBefore = 9f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        wheneverSuccessRLocalFluxo()
        wheneverSuccessTerceiro()
        wheneverSuccessVisitante()
        whenever(
            setCheckUpdateAllTable(FlagUpdate.UPDATED)
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 1).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateLocalTrab(result)
        checkResultUpdateRLocalFluxo(result)
        checkResultUpdateTerceiro(result)
        checkResultUpdateVisitante(result)
        assertEquals(
            result[27],
            ConfigState(
                flagDialog = true,
                flagProgress = true,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return success if saveTokenAndUpdateAllDatabase is success`() = runTest {
        wheneverSuccessToken()
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        wheneverSuccessRLocalFluxo()
        wheneverSuccessTerceiro()
        wheneverSuccessVisitante()
        val viewModel = getViewModel()
        viewModel.onNumberChanged("16997417840")
        viewModel.onPasswordChanged("12345")
        viewModel.updateVersion("6.00")
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Atualização de dados realizado com sucesso!"
        )
    }

    private fun wheneverSuccessToken() = runTest {
        whenever(
            sendDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(
            Result.success(1)
        )
        whenever(
            saveDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(
            Result.success(true)
        )
    }

    private fun wheneverSuccessChave() =
        runTest {
            whenever(
                updateTableChave(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_chave",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_chave",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateChave(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_chave",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessColab() = runTest {
        whenever(
            updateTableColab(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateColab(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessEquip() = runTest {
        whenever(
            updateTableEquip(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateEquip(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_equip",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessFluxo() = runTest {
        whenever(
            updateTableFluxo(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_fluxo",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateFluxo(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_fluxo",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_fluxo",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessLocal() = runTest {
        whenever(
            updateTableLocal(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateLocal(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_local",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessLocalTrab() = runTest {
        whenever(
            updateTableLocalTrab(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local_trab",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_local_trab do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local_trab",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateLocalTrab(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local_trab",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_local_trab do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_local_trab",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessRLocalFluxo() = runTest {
        whenever(
            updateTableRLocalFluxo(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_local_fluxo",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_local_fluxo",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateRLocalFluxo(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_r_local_fluxo",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_r_local_fluxo",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessTerceiro() = runTest {
        whenever(
            updateTableTerceiro(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_terceiro",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateTerceiro(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessVisitante() = runTest {
        whenever(
            updateTableVisitante(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_visitante",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateVisitante(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_visitante",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

}
