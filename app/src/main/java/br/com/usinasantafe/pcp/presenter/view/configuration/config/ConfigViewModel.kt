package br.com.usinasantafe.pcp.presenter.view.configuration.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.pcp.domain.usecases.config.SetFinishUpdateAllTable
import br.com.usinasantafe.pcp.domain.usecases.config.UpdateConfig
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableChave
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableFluxo
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableLocal
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableLocalTrab
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableRLocalFluxo
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableTerceiro
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableVisitante
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.FlagUpdate
import br.com.usinasantafe.pcp.lib.LevelUpdate
import br.com.usinasantafe.pcp.utils.UiStateWithStatusUpdate
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.collectUpdateStep
import br.com.usinasantafe.pcp.utils.executeUpdateSteps
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureUpdate
import br.com.usinasantafe.pcp.utils.onSuccessUpdateFinish
import br.com.usinasantafe.pcp.utils.percentage
import br.com.usinasantafe.pcp.utils.sizeUpdate
import br.com.usinasantafe.pcp.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.invoke

data class ConfigState(
    val number: String = "",
    val password: String = "",
    val version: String = "",
    override val status: UiStatusStateUpdate = UiStatusStateUpdate()
) : UiStateWithStatusUpdate<ConfigState> {

    override fun copyWithStatus(status: UiStatusStateUpdate): ConfigState =
        copy(status = status)

}

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val getConfigInternal: GetConfigInternal,
    private val updateConfig: UpdateConfig,
    private val updateTableChave: UpdateTableChave,
    private val updateTableColab: UpdateTableColab,
    private val updateTableEquip: UpdateTableEquip,
    private val updateTableFluxo: UpdateTableFluxo,
    private val updateTableLocal: UpdateTableLocal,
    private val updateTableLocalTrab: UpdateTableLocalTrab,
    private val updateTableRLocalFluxo: UpdateTableRLocalFluxo,
    private val updateTableTerceiro: UpdateTableTerceiro,
    private val updateTableVisitante: UpdateTableVisitante,
    private val setFinishUpdateAllTable: SetFinishUpdateAllTable,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ConfigState.() -> ConfigState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState {
        val flag = (status.levelUpdate == LevelUpdate.FINISH_UPDATE_COMPLETED)
        copy(status = status.copy(flagDialog = false, flagFailure = false, flagAccess = flag))
    }

    fun onNumberChanged(v: String) = updateState { copy(number = v) }
    fun onPasswordChanged(v: String) = updateState { copy(password = v) }
    fun onVersionChanged(v: String) = updateState { copy(version = v) }

    private fun ConfigState.isValid() = number.isNotBlank() && password.isNotBlank()

    fun recoverDataConfig() = viewModelScope.launch {

        data class RecoverConfig(
            val number: String,
            val password: String
        )

        runCatching {
            val config = getConfigInternal().getOrThrow()
            RecoverConfig(
                number = config?.number ?: "",
                password = config?.password ?: "",
            )
        }
            .onSuccess {
                updateState { copy(number = it.number, password = it.password) }
            }
            .onFailureUpdate(::updateState)
    }

    fun onSaveAndUpdate() = viewModelScope.launch {
        if (!state.isValid()) {
            updateState { withFailure(Errors.FIELD_EMPTY) }
            return@launch
        }

        val ok = updateConfig(state.number, state.password, state.version, sizeAll = 3f, count = 1f)
            .collectUpdateStep(getClassAndMethod(), state.status, true) { status ->
                updateState { copyWithStatus(status) }
            }

        if (ok) {
            updateAllDatabase().collect { _uiState.value = it }
            if (!state.status.flagFailure) {
                setFinishUpdateAllTable()
                    .onSuccessUpdateFinish(::updateState)
                    .onFailureUpdate(::updateState, Errors.UPDATE, true)
            }
        }
    }

    suspend fun updateAllDatabase(): Flow<ConfigState> {
        return executeUpdateSteps(
            steps = listUpdate(),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
            flagUpdateFinish = false,
            flagProgressOnFailure = true
        )
    }

    suspend fun listUpdate() : List<Flow<UiStatusStateUpdate>> {
        val tables = listOf(
            updateTableChave::invoke,
            updateTableColab::invoke,
            updateTableEquip::invoke,
            updateTableFluxo::invoke,
            updateTableLocal::invoke,
            updateTableLocalTrab::invoke,
            updateTableRLocalFluxo::invoke,
            updateTableTerceiro::invoke,
            updateTableVisitante::invoke,
        )
        val sizeAllTables = sizeUpdate(tables.size.toFloat())
        return tables.mapIndexed { index, useCase ->
            useCase(sizeAllTables, (index + 1).toFloat())
        }
    }

}