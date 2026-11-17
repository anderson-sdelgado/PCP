package br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.placa

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetPlacaResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.SetPlacaResidencia
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.observ.ObservProprioState
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureState
import br.com.usinasantafe.pcp.utils.onFailureUpdate
import br.com.usinasantafe.pcp.utils.onSuccessStateAccess
import br.com.usinasantafe.pcp.utils.onSuccessUpdateCheckAccess
import br.com.usinasantafe.pcp.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class PlacaResidenciaState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val text: String = "",
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<PlacaResidenciaState> {

    override fun copyWithStatus(status: UiStatusState): PlacaResidenciaState =
        copy(status = status)

}

@HiltViewModel
class PlacaResidenciaViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPlacaResidencia: GetPlacaResidencia,
    private val setPlacaResidencia: SetPlacaResidencia
) : ViewModel() {

    private val flowAppArgs: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val idArgs: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(PlacaResidenciaState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: PlacaResidenciaState.() -> PlacaResidenciaState) {
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
            getPlacaResidencia(state.id).getOrThrow()
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
            setPlacaResidencia(state.text, state.flowApp, state.id).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }

}