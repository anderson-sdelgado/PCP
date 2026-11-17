package br.com.usinasantafe.pcp.presenter.view.chaveEquip.controleeditlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.CloseAllMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.GetMovChaveEquipOpenList
import br.com.usinasantafe.pcp.presenter.model.ControleChaveEquipModel
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.onFailureState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ControleChaveEquipEditListState(
    val list: List<ControleChaveEquipModel> = listOf(),
    val flagDialogCheck: Boolean = false,
    val flagCloseAllMov: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<ControleChaveEquipEditListState> {

    override fun copyWithStatus(status: UiStatusState): ControleChaveEquipEditListState =
        copy(status = status)

}

@HiltViewModel
class ControleChaveEquipEditListViewModel @Inject constructor(
    private val getMovChaveEquipOpenList: GetMovChaveEquipOpenList,
    private val closeAllMovChaveEquip: CloseAllMovChaveEquip,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ControleChaveEquipEditListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ControleChaveEquipEditListState.() -> ControleChaveEquipEditListState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDialogCheck(flag: Boolean) = updateState { copy(flagDialogCheck = flag) }

    fun onCloseAllMov() = viewModelScope.launch {
        runCatching {
            closeAllMovChaveEquip().getOrThrow()
        }
            .onSuccess { updateState { copy(flagCloseAllMov = true, status = status.copy(flagDialog = false)) } }
            .onFailureState(::updateState)
    }

    fun list() = viewModelScope.launch {
        runCatching {
            getMovChaveEquipOpenList().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureState(::updateState)
    }

}