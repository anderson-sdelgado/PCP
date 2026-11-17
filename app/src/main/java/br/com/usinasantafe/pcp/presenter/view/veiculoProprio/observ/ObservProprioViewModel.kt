package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.observ

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetObservProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetTypeMov
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SaveMovEquipProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SetObservProprio
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
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

data class ObservProprioState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val typeMov: TypeMovEquip = TypeMovEquip.INPUT,
    val text: String? = null,
    val flagReturn: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<ObservProprioState> {

    override fun copyWithStatus(status: UiStatusState): ObservProprioState =
        copy(status = status)

}

@HiltViewModel
class ObservProprioViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setObservProprio: SetObservProprio,
    private val getObservProprio: GetObservProprio,
    private val saveMovEquipProprio: SaveMovEquipProprio,
    private val getTypeMov: GetTypeMov
) : ViewModel() {

    private val flowAppArgs: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val idArgs: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservProprioState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ObservProprioState.() -> ObservProprioState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        updateState { copy(flowApp = flowApp, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onChanged(text: String) = updateState { copy(text = text) }

    fun recover() = viewModelScope.launch {
        runCatching {
            if(state.flowApp == FlowApp.ADD) return@launch
            getObservProprio(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) } }
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            setObservProprio(state.text, state.flowApp, state.id).getOrThrow()
            if(state.flowApp == FlowApp.ADD) saveMovEquipProprio().getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }

    fun onReturn() = viewModelScope.launch {
        runCatching {
            if(state.flowApp == FlowApp.CHANGE) return@runCatching
            val typeMov = getTypeMov().getOrThrow()
            updateState { copy(typeMov = typeMov) }
        }
            .onSuccess { updateState { copy(flagReturn = true) }}
            .onFailureState(::updateState)
    }
}