package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.matriccolab

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetMatricColab
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcp.presenter.theme.addTextField
import br.com.usinasantafe.pcp.presenter.theme.clearTextField
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.utils.UiStateWithStatusUpdate
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.executeUpdateSteps
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureUpdate
import br.com.usinasantafe.pcp.utils.onSuccessUpdateCheckAccess
import br.com.usinasantafe.pcp.utils.sizeUpdate
import br.com.usinasantafe.pcp.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatricColabState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val text: String = "",
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    override val status: UiStatusStateUpdate = UiStatusStateUpdate()
) : UiStateWithStatusUpdate<MatricColabState> {

    override fun copyWithStatus(status: UiStatusStateUpdate): MatricColabState =
        copy(status = status)

}

@HiltViewModel
class MatricColabViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val checkMatricColab: CheckMatricColab,
    private val updateTableColab: UpdateTableColab,
    private val getMatricColab: GetMatricColab,
) : ViewModel() {

    private val flowAppArgs: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupanteArgs: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(MatricColabState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: MatricColabState.() -> MatricColabState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        val typeOcupante = TypeOcupante.entries[typeOcupanteArgs]
        updateState { copy(flowApp = flowApp, typeOcupante = typeOcupante, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recover() = viewModelScope.launch {
        runCatching {
            val check = (state.flowApp == FlowApp.CHANGE) && (state.typeOcupante == TypeOcupante.MOTORISTA)
            if(!check) return@launch
            getMatricColab(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) }}
            .onFailureUpdate(::updateState)
    }

    fun onTextField(text: String, typeButton: TypeButton) {
        when(typeButton) {
            TypeButton.NUMERIC -> updateState { copy(text = addTextField(this.text, text)) }
            TypeButton.CLEAN -> updateState { copy(text = clearTextField(this.text)) }
            TypeButton.OK -> set()
            TypeButton.UPDATE -> {
                viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
            }
        }
    }

    private fun set() = viewModelScope.launch {
        runCatching {
            if(state.text.isEmpty()) {
                updateState { withFailure( Errors.FIELD_EMPTY) }
                return@launch
            }
            checkMatricColab(uiState.value.text).getOrThrow()
        }
            .onSuccessUpdateCheckAccess(::updateState)
            .onFailureUpdate( ::updateState)
    }

    suspend fun updateAllDatabase(): Flow<MatricColabState> =
        executeUpdateSteps(
            steps = listOf(updateTableColab(sizeUpdate())),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}