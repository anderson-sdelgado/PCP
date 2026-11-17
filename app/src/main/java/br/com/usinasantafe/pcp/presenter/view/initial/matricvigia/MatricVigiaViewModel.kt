package br.com.usinasantafe.pcp.presenter.view.initial.matricvigia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcp.domain.usecases.config.SetMatricVigiaConfig
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.pcp.presenter.theme.addTextField
import br.com.usinasantafe.pcp.presenter.theme.clearTextField
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.presenter.view.chave.matriccolab.MatricColabChaveState
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class MatricVigiaState(
    val text: String = "",
    override val status: UiStatusStateUpdate = UiStatusStateUpdate()
) : UiStateWithStatusUpdate<MatricVigiaState> {

    override fun copyWithStatus(status: UiStatusStateUpdate): MatricVigiaState =
        copy(status = status)

}

@HiltViewModel
class MatricVigiaViewModel @Inject constructor(
    private val checkMatricColab: CheckMatricColab,
    private val setMatricVigiaConfig: SetMatricVigiaConfig,
    private val updateTableColab: UpdateTableColab,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatricVigiaState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: MatricVigiaState.() -> MatricVigiaState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onTextField(text: String, typeButton: TypeButton){
        when(typeButton){
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
            if(!checkMatricColab(state.text).getOrThrow()){
                updateState { withFailure( Errors.INVALID) }
                return@launch
            }
            setMatricVigiaConfig(state.text).getOrThrow()
        }
            .onSuccessUpdateCheckAccess(::updateState)
            .onFailureUpdate(::updateState)
    }

    suspend fun updateAllDatabase(): Flow<MatricVigiaState> =
        executeUpdateSteps(
            steps = listOf(updateTableColab(sizeUpdate())),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )
}