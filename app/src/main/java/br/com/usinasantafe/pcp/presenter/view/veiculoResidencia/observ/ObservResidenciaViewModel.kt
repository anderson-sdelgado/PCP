package br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.observ

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetObservResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.SaveMovEquipResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.SetObservResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.StartOutputMovEquipResidencia
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.observ.ObservProprioState
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureState
import br.com.usinasantafe.pcp.utils.onSuccessStateAccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ObservResidenciaState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeMov: TypeMovEquip = TypeMovEquip.INPUT,
    val id: Int = 0,
    val text: String? = null,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<ObservResidenciaState> {

    override fun copyWithStatus(status: UiStatusState): ObservResidenciaState =
        copy(status = status)

}

@HiltViewModel
class ObservResidenciaViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setObservResidencia: SetObservResidencia,
    private val getObservResidencia: GetObservResidencia,
    private val startOutputMovEquipResidencia: StartOutputMovEquipResidencia,
    private val saveMovEquipResidencia: SaveMovEquipResidencia
) : ViewModel() {

    private val typeMovArgs: Int = savedStateHandle[TYPE_MOV_ARGS]!!
    private val flowAppArgs: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val idArgs: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservResidenciaState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ObservResidenciaState.() -> ObservResidenciaState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        val typeMov = TypeMovEquip.entries[typeMovArgs]
        updateState { copy(flowApp = flowApp, typeMov = typeMov, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onChanged(text: String) = updateState { copy(text = text) }

    fun recover() = viewModelScope.launch {
        runCatching {
            if(state.flowApp == FlowApp.ADD) return@launch
            getObservResidencia(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) } }
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            val check = (state.typeMov == TypeMovEquip.OUTPUT) &&  (state.flowApp == FlowApp.ADD)
            if(check) startOutputMovEquipResidencia(state.id).getOrThrow()
            setObservResidencia(state.text, state.flowApp, state.id).getOrThrow()
            if(state.flowApp == FlowApp.ADD) saveMovEquipResidencia(state.typeMov, state.id).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }

}