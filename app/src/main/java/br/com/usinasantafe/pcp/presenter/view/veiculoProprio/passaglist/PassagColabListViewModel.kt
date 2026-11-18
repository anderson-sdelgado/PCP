package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.passaglist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.CleanPassagColab
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.DeletePassagColab
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetPassagColabList
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.onFailureState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PassagColabListState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val list: List<Colab> = emptyList(),
    val matricColabSelected: Int? = null,
    val flagDialogCheck: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<PassagColabListState> {

    override fun copyWithStatus(status: UiStatusState): PassagColabListState =
        copy(status = status)

}

@HiltViewModel
class PassagColabListViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val cleanPassagColab: CleanPassagColab,
    private val getPassagColabList: GetPassagColabList,
    private val deletePassagColab: DeletePassagColab,
) : ViewModel() {

    private val flowAppArgs: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupanteArgs: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(PassagColabListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: PassagColabListState.() -> PassagColabListState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        val typeOcupante = TypeOcupante.entries[typeOcupanteArgs]
        updateState { copy(flowApp = flowApp, typeOcupante = typeOcupante, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onCloseDialogCheck() = updateState { copy(flagDialogCheck = false) }

    fun setDelete(matricColab: Int) = updateState { copy(matricColabSelected = matricColab, flagDialogCheck = true) }

    fun recover(flagStart: Boolean) = viewModelScope.launch {
        runCatching {
            val check = (state.flowApp == FlowApp.ADD) && (uiState.value.typeOcupante == TypeOcupante.MOTORISTA) && (flagStart)
            if (check) cleanPassagColab().getOrThrow()
            getPassagColabList(state.flowApp, state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureState(::updateState)
    }

    fun onDelete() = viewModelScope.launch {
        runCatching {
            deletePassagColab(state.matricColabSelected!!, state.flowApp, state.id).getOrThrow()
            updateState { copy(flagDialogCheck = false) }
        }
            .onSuccess { recover(false) }
            .onFailureState(::updateState)
    }

}