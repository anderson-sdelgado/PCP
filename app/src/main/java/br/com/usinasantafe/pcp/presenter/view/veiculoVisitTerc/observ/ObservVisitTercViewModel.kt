package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.observ

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetObservVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.SaveMovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.SetObservVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.StartOutputMovEquipVisitTerc
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.observ.ObservResidenciaState
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

data class ObservVisitTercState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val typeMov: TypeMovEquip = TypeMovEquip.INPUT,
    val text: String? = null,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<ObservVisitTercState> {

    override fun copyWithStatus(status: UiStatusState): ObservVisitTercState =
        copy(status = status)

}

@HiltViewModel
class ObservVisitTercViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setObservVisitTerc: SetObservVisitTerc,
    private val getObservVisitTerc: GetObservVisitTerc,
    private val startOutputMovEquipVisitTerc: StartOutputMovEquipVisitTerc,
    private val saveMovEquipVisitTerc: SaveMovEquipVisitTerc
) : ViewModel() {

    private val typeMovArgs: Int = savedStateHandle[TYPE_MOV_ARGS]!!
    private val flowAppArgs: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val idArgs: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservVisitTercState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ObservVisitTercState.() -> ObservVisitTercState) {
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
            getObservVisitTerc(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) } }
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            val check = (state.typeMov == TypeMovEquip.OUTPUT) &&  (state.flowApp == FlowApp.ADD)
            if(check) startOutputMovEquipVisitTerc(state.id).getOrThrow()
            setObservVisitTerc(state.text, state.flowApp, state.id).getOrThrow()
            if(state.flowApp == FlowApp.ADD) saveMovEquipVisitTerc(state.typeMov, state.id).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }

}