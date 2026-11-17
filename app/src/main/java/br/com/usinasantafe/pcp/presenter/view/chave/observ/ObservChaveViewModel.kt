package br.com.usinasantafe.pcp.presenter.view.chave.observ

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chave.GetObservMovChave
import br.com.usinasantafe.pcp.domain.usecases.chave.SaveMovChave
import br.com.usinasantafe.pcp.domain.usecases.chave.SetObservMovChave
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovKey
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.onFailureState
import br.com.usinasantafe.pcp.utils.onSuccessStateAccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ObservChaveState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeMov: TypeMovKey = TypeMovKey.REMOVE,
    val id: Int = 0,
    val text: String? = null,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<ObservChaveState> {

    override fun copyWithStatus(status: UiStatusState): ObservChaveState =
        copy(status = status)

}

@HiltViewModel
class ObservChaveViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setObservMovChave: SetObservMovChave,
    private val saveMovChave: SaveMovChave,
    private val getObservMovChave: GetObservMovChave
) : ViewModel() {

    private val typeMovArgs: Int = savedStateHandle[TYPE_MOV_ARGS]!!
    private val flowAppArgs: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val idArgs: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservChaveState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ObservChaveState.() -> ObservChaveState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        val typeMov = TypeMovKey.entries[typeMovArgs]
        updateState { copy(flowApp = flowApp, typeMov = typeMov, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onChanged(observ: String) = updateState { copy(text = observ) }

    fun recover() = viewModelScope.launch {
        runCatching {
            if(state.flowApp == FlowApp.ADD) return@launch
            getObservMovChave(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) } }
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            setObservMovChave(state.text, state.flowApp, state.id).getOrThrow()
            if(state.flowApp == FlowApp.ADD) saveMovChave(state.typeMov, state.id).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }
}