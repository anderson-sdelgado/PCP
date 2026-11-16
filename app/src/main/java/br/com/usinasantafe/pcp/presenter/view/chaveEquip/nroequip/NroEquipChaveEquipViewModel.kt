package br.com.usinasantafe.pcp.presenter.view.chaveEquip.nroequip

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.GetNroEquipMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.SetIdEquipMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.common.CheckNroEquip
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.theme.addTextField
import br.com.usinasantafe.pcp.presenter.theme.clearTextField
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.presenter.view.chave.matriccolab.MatricColabChaveState
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class NroEquipChaveEquipState(
    val text: String = "",
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    override val status: UiStatusStateUpdate = UiStatusStateUpdate()
) : UiStateWithStatusUpdate<NroEquipChaveEquipState> {

    override fun copyWithStatus(status: UiStatusStateUpdate): NroEquipChaveEquipState =
        copy(status = status)

}

@HiltViewModel
class NroEquipChaveEquipViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val checkNroEquip: CheckNroEquip,
    private val setIdEquipMovChaveEquip: SetIdEquipMovChaveEquip,
    private val updateTableEquip: UpdateTableEquip,
    private val getNroEquipMovChaveEquip: GetNroEquipMovChaveEquip,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NroEquipChaveEquipState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: NroEquipChaveEquipState.() -> NroEquipChaveEquipState) {
        _uiState.update(block)
    }

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                id = id
            )
        }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recoverNroEquip() = viewModelScope.launch {
        runCatching {
            if(uiState.value.flowApp == FlowApp.ADD) return@launch
            getNroEquipMovChaveEquip(uiState.value.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) }}
            .onFailureUpdate( ::updateState)
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
            if(!checkNroEquip(uiState.value.text).getOrThrow()){
                updateState { withFailure( Errors.INVALID) }
                return@launch
            }
            setIdEquipMovChaveEquip(uiState.value.text, uiState.value.flowApp, uiState.value.id).getOrThrow()
        }
            .onSuccessUpdateCheckAccess(::updateState)
            .onFailureUpdate(::updateState)
    }

    suspend fun updateAllDatabase(): Flow<NroEquipChaveEquipState> =
        executeUpdateSteps(
            steps = listOf(updateTableEquip(sizeUpdate())),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}