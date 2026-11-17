package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.nroequip

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.CheckNroEquip
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetNroEquipProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SetIdEquipProprio
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_EQUIP_ARGS
import br.com.usinasantafe.pcp.presenter.theme.addTextField
import br.com.usinasantafe.pcp.presenter.theme.clearTextField
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.lib.TypeEquip
import br.com.usinasantafe.pcp.utils.UiStateWithStatusUpdate
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.executeUpdateSteps
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureUpdate
import br.com.usinasantafe.pcp.utils.onSuccessUpdateCheckAccess
import br.com.usinasantafe.pcp.utils.sizeUpdate
import br.com.usinasantafe.pcp.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NroEquipProprioState(
    val text: String = "",
    val flowApp: FlowApp = FlowApp.ADD,
    val typeEquip: TypeEquip = TypeEquip.VEICULO,
    val id: Int = 0,
    override val status: UiStatusStateUpdate = UiStatusStateUpdate()
) : UiStateWithStatusUpdate<NroEquipProprioState> {

    override fun copyWithStatus(status: UiStatusStateUpdate): NroEquipProprioState =
        copy(status = status)

}

@HiltViewModel
class NroEquipProprioViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val checkNroEquip: CheckNroEquip,
    private val setIdEquipProprio: SetIdEquipProprio,
    private val updateTableEquip: UpdateTableEquip,
    private val getNroEquipProprio: GetNroEquipProprio,
) : ViewModel() {

    private val flowAppArgs: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeEquipArgs: Int = saveStateHandle[TYPE_EQUIP_ARGS]!!
    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NroEquipProprioState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: NroEquipProprioState.() -> NroEquipProprioState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        val typeEquip = TypeEquip.entries[typeEquipArgs]
        updateState { copy(flowApp = flowApp, typeEquip = typeEquip, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recover() = viewModelScope.launch {
        runCatching {
            val check = (state.flowApp == FlowApp.CHANGE) && (state.typeEquip == TypeEquip.VEICULO)
            if(!check) return@launch
            getNroEquipProprio(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) }}
            .onFailureUpdate(::updateState)
    }

    fun onTextField(text: String, typeButton: TypeButton) {
        when(typeButton) {
            TypeButton.NUMERIC -> updateState { copy(text = addTextField(this.text, text)) }
            TypeButton.CLEAN -> updateState { copy(text = clearTextField(this.text)) }
            TypeButton.OK -> set()
            TypeButton.UPDATE -> {
                viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
            }
        }
    }

    private fun set() = viewModelScope.launch {
        runCatching {
            if(state.text.isEmpty()) {
                updateState { withFailure( Errors.FIELD_EMPTY) }
                return@launch
            }
            if(!checkNroEquip(state.text).getOrThrow()){
                updateState { withFailure( Errors.INVALID) }
                return@launch
            }
            setIdEquipProprio(state.text, state.flowApp, state.typeEquip, state.id).getOrThrow()
        }
            .onSuccessUpdateCheckAccess(::updateState)
            .onFailureUpdate(::updateState)
    }

    suspend fun updateAllDatabase(): Flow<NroEquipProprioState> =
        executeUpdateSteps(
            steps = listOf(updateTableEquip(sizeUpdate())),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}