package br.com.usinasantafe.pcp.presenter.view.configuration.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
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
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.percentage
import br.com.usinasantafe.pcp.utils.sizeUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ConfigState(
    val number: String = "",
    val password: String = "",
    val version: String = "",
    val flagDialog: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val failure: String = "",
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToConfig(): ConfigState {
    val fail = if(failure.isNotEmpty()){
        val ret = "ConfigViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    val msg = if(failure.isNotEmpty()){
        "ConfigViewModel.updateAllDatabase -> ${this.failure}"
    } else {
        this.msgProgress
    }
    return ConfigState(
            flagDialog = this.flagDialog,
            flagFailure = this.flagFailure,
            errors = this.errors,
            failure = fail,
            flagProgress = this.flagProgress,
            msgProgress = msg,
            currentProgress = this.currentProgress,
        )
    }


@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val getConfigInternal: GetConfigInternal,
    private val sendDataConfig: SendDataConfig,
    private val saveDataConfig: SaveDataConfig,
    private val updateTableChave: UpdateTableChave,
    private val updateTableColab: UpdateTableColab,
    private val updateTableEquip: UpdateTableEquip,
    private val updateTableFluxo: UpdateTableFluxo,
    private val updateTableLocal: UpdateTableLocal,
    private val updateTableLocalTrab: UpdateTableLocalTrab,
    private val updateTableRLocalFluxo: UpdateTableRLocalFluxo,
    private val updateTableTerceiro: UpdateTableTerceiro,
    private val updateTableVisitante: UpdateTableVisitante,
    private val setCheckUpdateAllTable: SetCheckUpdateAllTable,
) : ViewModel() {

    private val qtdTable = 9f

    private val _uiState = MutableStateFlow(ConfigState())
    val uiState = _uiState.asStateFlow()

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun updateVersion(version: String) {
        _uiState.update {
            it.copy(version = version)
        }
    }

    fun onNumberChanged(number: String) {
        _uiState.update {
            it.copy(number = number)
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun returnDataConfig() = viewModelScope.launch {
        val recoverConfig = getConfigInternal()
        if (recoverConfig.isFailure) {
            val error = recoverConfig.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    errors = Errors.EXCEPTION,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = recoverConfig.getOrNull()
        result?.let { configModel ->
            _uiState.update {
                it.copy(
                    number = configModel.number,
                    password = configModel.password
                )
            }
        }
    }

    fun saveTokenAndUpdateAllDatabase() {
        if (
            uiState.value.number.isEmpty() ||
            uiState.value.password.isEmpty()
        ) {
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.FIELD_EMPTY,
                    flagFailure = true,
                )
            }
            return
        }
        viewModelScope.launch {
            token().collect { configStateToken ->
                _uiState.value = configStateToken
                if (
                    (!configStateToken.flagFailure) &&
                    (configStateToken.currentProgress == 1f)
                ) {
                    updateAllDatabase().collect { configStateUpdate ->
                        _uiState.value = configStateUpdate
                    }
                }
            }
        }
    }

    suspend fun token(): Flow<ConfigState> = flow {
        val sizeToken = 3f
        val number = uiState.value.number
        val password = uiState.value.password
        val version = uiState.value.version
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = percentage(1f, sizeToken)
            )
        )
        val resultSend = sendDataConfig(
            number = number,
            password = password,
            version = version
        )
        if (resultSend.isFailure) {
            val error = resultSend.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            emit(
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados de Token",
                currentProgress = percentage(2f, sizeToken),
            )
        )
        val resultSave = saveDataConfig(
            number = number,
            password = password,
            version = version,
            idBD = resultSend.getOrNull()!!
        )
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            emit(
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Ajuste iniciais finalizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    suspend fun updateAllDatabase(): Flow<ConfigState> = flow {
        var pos = 0f
        val sizeUpdate = sizeUpdate(qtdTable)
        var configState = ConfigState()
        updateTableChave(sizeUpdate, ++pos).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableColab(sizeUpdate, ++pos).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableEquip(sizeUpdate, ++pos).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableFluxo(sizeUpdate, ++pos).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableLocal(sizeUpdate, ++pos).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableLocalTrab(sizeUpdate, ++pos).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableRLocalFluxo(sizeUpdate, ++pos).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableTerceiro(sizeUpdate, ++pos).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        updateTableVisitante(sizeUpdate, ++pos).collect {
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if (configState.flagFailure) return@flow
        val result = setCheckUpdateAllTable(FlagUpdate.UPDATED)
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            emit(
                ConfigState(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    failure = failure,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagDialog = true,
                flagProgress = true,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

}