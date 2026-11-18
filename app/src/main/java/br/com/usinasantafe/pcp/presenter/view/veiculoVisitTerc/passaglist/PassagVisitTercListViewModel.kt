package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.passaglist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.CleanPassagVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.DeletePassagVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetPassagVisitTercList
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.presenter.model.PassagVisitTercModel
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.passaglist.PassagColabListState
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class PassagVisitTercListState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val list: List<PassagVisitTercModel> = emptyList(),
    val idVisitTercSelected: Int? = null,
    val flagDialogCheck: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<PassagVisitTercListState> {

    override fun copyWithStatus(status: UiStatusState): PassagVisitTercListState =
        copy(status = status)

}

@HiltViewModel
class PassagVisitTercListViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val cleanPassagVisitTerc: CleanPassagVisitTerc,
    private val getPassagVisitTercList: GetPassagVisitTercList,
    private val deletePassagVisitTerc: DeletePassagVisitTerc,
) : ViewModel() {

    private val flowAppArgs: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupanteArgs: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(PassagVisitTercListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: PassagVisitTercListState.() -> PassagVisitTercListState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        val typeOcupante = TypeOcupante.entries[typeOcupanteArgs]
        updateState { copy(flowApp = flowApp, typeOcupante = typeOcupante, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onCloseDialogCheck() = updateState { copy(flagDialogCheck = false) }

    fun setDelete(idVisitTerc: Int) = updateState { copy(idVisitTercSelected = idVisitTerc, flagDialogCheck = true) }

    fun recover(flagStart: Boolean) = viewModelScope.launch {
        runCatching {
            val check = (state.flowApp == FlowApp.ADD) && (uiState.value.typeOcupante == TypeOcupante.MOTORISTA) && (flagStart)
            if (check) cleanPassagVisitTerc().getOrThrow()
            getPassagVisitTercList(state.flowApp, state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureState(::updateState)
    }

    fun onDelete() = viewModelScope.launch {
        runCatching {
            deletePassagVisitTerc(state.idVisitTercSelected!!, state.flowApp, state.id).getOrThrow()
            updateState { copy(flagDialogCheck = false) }
        }
            .onSuccess { recover(false) }
            .onFailureState(::updateState)
    }

}