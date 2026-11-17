package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.notafiscal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetNotaFiscalProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SetNotaFiscalProprio
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.theme.addTextField
import br.com.usinasantafe.pcp.presenter.theme.clearTextField
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
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

data class NotaFiscalState(
    val text: String = "",
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<NotaFiscalState> {

    override fun copyWithStatus(status: UiStatusState): NotaFiscalState =
        copy(status = status)

}

@HiltViewModel
class NotaFiscalViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setNotaFiscalProprio: SetNotaFiscalProprio,
    private val getNotaFiscalProprio: GetNotaFiscalProprio
) : ViewModel() {

    private val flowAppArgs: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val idArgs: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NotaFiscalState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: NotaFiscalState.() -> NotaFiscalState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        updateState { copy(flowApp = flowApp, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recover() = viewModelScope.launch {
        runCatching {
            if(state.flowApp == FlowApp.ADD) return@launch
            getNotaFiscalProprio(state.id).getOrThrow() ?: ""
        }
            .onSuccess { updateState { copy(text = it) }}
            .onFailureState(::updateState)

    }

    fun onTextField(text: String, typeButton: TypeButton) {
        when(typeButton) {
            TypeButton.NUMERIC -> updateState { copy(text = addTextField(this.text, text)) }
            TypeButton.CLEAN -> updateState { copy(text = clearTextField(this.text)) }
            TypeButton.OK -> set()
            TypeButton.UPDATE -> {}
        }
    }

    private fun set() = viewModelScope.launch {
        runCatching {
            val nf = if(state.text == "") null else state.text
            setNotaFiscalProprio(nf, state.flowApp, state.id).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState( ::updateState)
    }

}