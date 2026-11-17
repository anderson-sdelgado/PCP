package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.destino

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetDestinoProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetTypeMov
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SetDestinoProprio
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.onFailureState
import br.com.usinasantafe.pcp.utils.onSuccessStateAccess
import br.com.usinasantafe.pcp.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DestinoProprioState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val text: String = "",
    val typeMov: TypeMovEquip = TypeMovEquip.INPUT,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<DestinoProprioState> {

    override fun copyWithStatus(status: UiStatusState): DestinoProprioState =
        copy(status = status)

}

@HiltViewModel
class DestinoProprioViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setDestinoProprio: SetDestinoProprio,
    private val getDestinoProprio: GetDestinoProprio,
    private val getTypeMov: GetTypeMov
) : ViewModel() {

    private val flowAppArgs: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val idArgs: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DestinoProprioState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: DestinoProprioState.() -> DestinoProprioState) {
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
            getDestinoProprio(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) } }
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            if(state.text.isEmpty()) {
                updateState { withFailure( Errors.FIELD_EMPTY) }
                return@launch
            }
            setDestinoProprio(state.text, state.flowApp, state.id).getOrThrow()
            if(state.flowApp == FlowApp.ADD) {
                val typeMov = getTypeMov().getOrThrow()
                updateState { copy(typeMov = typeMov) }
            }
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }

}