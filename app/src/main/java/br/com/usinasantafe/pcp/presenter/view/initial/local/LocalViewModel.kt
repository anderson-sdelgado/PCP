package br.com.usinasantafe.pcp.presenter.view.initial.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.usecases.config.SetIdLocalConfig
import br.com.usinasantafe.pcp.domain.usecases.initial.GetLocalList
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableLocal
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.UiStateWithStatusUpdate
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.executeUpdateSteps
import br.com.usinasantafe.pcp.utils.onFailureUpdate
import br.com.usinasantafe.pcp.utils.onSuccessUpdateCheckAccess
import br.com.usinasantafe.pcp.utils.sizeUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LocalState(
    val locals: List<Local> = emptyList(),
    override val status: UiStatusStateUpdate = UiStatusStateUpdate()
) : UiStateWithStatusUpdate<LocalState> {

    override fun copyWithStatus(status: UiStatusStateUpdate): LocalState =
        copy(status = status)

}

@HiltViewModel
class LocalViewModel @Inject constructor(
    private val getLocalList: GetLocalList,
    private val setIdLocalConfig: SetIdLocalConfig,
    private val updateTableLocal: UpdateTableLocal,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocalState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: LocalState.() -> LocalState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun list() = viewModelScope.launch {
        runCatching {
            getLocalList().getOrThrow()
        }
            .onSuccess { updateState { copy(locals = it) }}
            .onFailureUpdate(::updateState)
    }

    fun set(id: Int) = viewModelScope.launch {
        runCatching {
            setIdLocalConfig(id).getOrThrow()
        }
            .onSuccessUpdateCheckAccess(::updateState)
            .onFailureUpdate( ::updateState)
    }

    fun updateDatabase() = viewModelScope.launch {
        updateAllDatabase().collect { stateUpdate ->
            _uiState.value = stateUpdate
        }
    }

    suspend fun updateAllDatabase(): Flow<LocalState> =
        executeUpdateSteps(
            steps = listOf(updateTableLocal(sizeUpdate())),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}