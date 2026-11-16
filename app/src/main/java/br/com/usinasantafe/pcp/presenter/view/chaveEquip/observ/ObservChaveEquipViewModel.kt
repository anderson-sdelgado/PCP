package br.com.usinasantafe.pcp.presenter.view.chaveEquip.observ

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.GetObservMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.SaveMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.SetObservMovChaveEquip
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovKey
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

data class ObservChaveEquipState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeMov: TypeMovKey = TypeMovKey.RECEIPT,
    val id: Int = 0,
    val text: String? = null,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<ObservChaveEquipState> {

    override fun copyWithStatus(status: UiStatusState): ObservChaveEquipState =
        copy(status = status)

}

@HiltViewModel
class ObservChaveEquipViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setObservMovChaveEquip: SetObservMovChaveEquip,
    private val saveMovChaveEquip: SaveMovChaveEquip,
    private val getObservMovChaveEquip: GetObservMovChaveEquip
) : ViewModel() {

    private val typeMov: Int = savedStateHandle[TYPE_MOV_ARGS]!!
    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservChaveEquipState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ObservChaveEquipState.() -> ObservChaveEquipState) {
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

    fun onObservChanged(text: String) = updateState { copy(text = text) }

    fun recoverObserv() = viewModelScope.launch {
        runCatching {
            if(uiState.value.flowApp == FlowApp.ADD) return@launch
            getObservMovChaveEquip(uiState.value.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) } }
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            setObservMovChaveEquip(uiState.value.text, uiState.value.flowApp, uiState.value.id).getOrThrow()
            if(uiState.value.flowApp == FlowApp.ADD) saveMovChaveEquip(uiState.value.typeMov, uiState.value.id).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }
}