package br.com.usinasantafe.pcp.presenter.view.chave.chavelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chave.GetChaveList
import br.com.usinasantafe.pcp.domain.usecases.chave.SetIdChaveMovChave
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableChave
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableLocalTrab
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.LevelUpdate
import br.com.usinasantafe.pcp.utils.UiStateWithStatusUpdate
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.executeUpdateSteps
import br.com.usinasantafe.pcp.utils.onFailureUpdate
import br.com.usinasantafe.pcp.utils.onSuccessUpdateAccess
import br.com.usinasantafe.pcp.utils.sizeUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChaveListState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val flagFilter: Boolean = false,
    val chaveList: List<ChaveModel> = emptyList(),
    val allChaveList: List<ChaveModel> = emptyList(),
    val field: String = "",
    override val status: UiStatusStateUpdate = UiStatusStateUpdate()
) : UiStateWithStatusUpdate<ChaveListState> {

    override fun copyWithStatus(status: UiStatusStateUpdate): ChaveListState =
        copy(status = status)

}

@HiltViewModel
class ChaveListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getChaveList: GetChaveList,
    private val setIdChaveMovChave: SetIdChaveMovChave,
    private val updateTableChave: UpdateTableChave,
    private val updateTableLocalTrab: UpdateTableLocalTrab
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ChaveListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ChaveListState.() -> ChaveListState) {
        _uiState.update(block)
    }

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                id = id
            )
        }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onFieldChanged(field: String) {
        val fieldUpper = field.uppercase()
        val baseList = state.allChaveList.ifEmpty { state.chaveList }
        updateState {
            if (fieldUpper.isNotEmpty()) {
                copy(
                    chaveList = baseList.filter { it.description.contains(fieldUpper) },
                    allChaveList = baseList,
                    flagFilter = true,
                    field = fieldUpper
                )
            } else {
                copy(
                    chaveList = baseList,
                    flagFilter = false,
                    field = fieldUpper
                )
            }
        }
    }

    fun recoverList() = viewModelScope.launch {
        runCatching {
            if(uiState.value.flagFilter) return@launch
            getChaveList().getOrThrow()
        }
            .onSuccess { updateState { copy(chaveList = it, allChaveList = it) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun setIdChave(id: Int) = viewModelScope.launch {
        runCatching {
            setIdChaveMovChave(id, uiState.value.flowApp, uiState.value.id).getOrThrow()
        }
            .onSuccessUpdateAccess(::updateState)
            .onFailureUpdate(getClassAndMethod(), ::updateState)

    }

    fun updateDatabase() = viewModelScope.launch {
        updateAllDatabase().collect { stateUpdate ->
            _uiState.value = stateUpdate
        }
        if (_uiState.value.status.levelUpdate == LevelUpdate.FINISH_UPDATE_COMPLETED) {
            recoverList()
        }
    }

    suspend fun updateAllDatabase(): Flow<ChaveListState> =
        executeUpdateSteps(
            steps = listUpdate(),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
            flagUpdateFinish = false
        )

    suspend fun listUpdate() : List<Flow<UiStatusStateUpdate>> {
        val sizeAll = sizeUpdate(2f)
        val list = mutableListOf<Flow<UiStatusStateUpdate>>()
        var count = 0f
        list.add(updateTableChave(sizeAll, ++count))
        list.add(updateTableLocalTrab(sizeAll, ++count))
        return list
    }

}