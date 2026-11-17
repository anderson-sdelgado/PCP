package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.equipseglist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.CleanEquipSeg
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.DeleteEquipSeg
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetEquipSegList
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_EQUIP_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeEquip
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.onFailureState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EquipSegListState(
    val list: List<Equip> = emptyList(),
    val flowApp: FlowApp = FlowApp.ADD,
    val typeEquip: TypeEquip = TypeEquip.VEICULO,
    val id: Int = 0,
    val idEquipSelected: Int? = null,
    val flagDialogCheck: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<EquipSegListState> {

    override fun copyWithStatus(status: UiStatusState): EquipSegListState =
        copy(status = status)

}

@HiltViewModel
class EquipSegListViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val cleanEquipSeg: CleanEquipSeg,
    private val getEquipSegList: GetEquipSegList,
    private val deleteEquipSeg: DeleteEquipSeg,
) : ViewModel() {

    private val flowAppArgs: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeEquipArgs: Int = saveStateHandle[TYPE_EQUIP_ARGS]!!
    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(EquipSegListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: EquipSegListState.() -> EquipSegListState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        val typeEquip = TypeEquip.entries[typeEquipArgs]
        updateState { copy(flowApp = flowApp, typeEquip = typeEquip, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onCloseDialogCheck() = updateState { copy(flagDialogCheck = false) }

    fun setDelete(idEquip: Int) = updateState { copy(idEquipSelected = idEquip, flagDialogCheck = true) }

    fun recover(flagStart: Boolean) = viewModelScope.launch {
        runCatching {
            if(flagStart) {
                val check = (state.flowApp == FlowApp.ADD) && (state.typeEquip == TypeEquip.VEICULO)
                if (check) cleanEquipSeg().getOrThrow()
            }
            getEquipSegList(state.flowApp, state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureState(::updateState)
    }

    fun onDelete() = viewModelScope.launch {
        runCatching {
            deleteEquipSeg(state.idEquipSelected!!, state.flowApp, state.id).getOrThrow()
            updateState { copy(flagDialogCheck = false) }
        }
            .onSuccess { recover(false) }
            .onFailureState(::updateState)
    }

}