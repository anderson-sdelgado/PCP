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

    private val typeMov: Int = savedStateHandle[TYPE_MOV_ARGS]!!
    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservChaveState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ObservChaveState.() -> ObservChaveState) {
        _uiState.update(block)
    }

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                typeMov = TypeMovKey.entries[typeMov],
                id = id
            )
        }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onObservChanged(observ: String) = updateState { copy(text = observ) }

    fun recoverObserv() = viewModelScope.launch {
        runCatching {
            if(uiState.value.flowApp == FlowApp.ADD) return@launch
            getObservMovChave(uiState.value.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) } }
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            setObservMovChave(uiState.value.text, uiState.value.flowApp, uiState.value.id).getOrThrow()
            if(uiState.value.flowApp == FlowApp.ADD) saveMovChave(uiState.value.typeMov, uiState.value.id).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }
}