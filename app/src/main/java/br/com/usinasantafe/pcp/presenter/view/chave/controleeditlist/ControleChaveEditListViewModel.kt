package br.com.usinasantafe.pcp.presenter.view.chave.controleeditlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chave.CloseAllMovChave
import br.com.usinasantafe.pcp.domain.usecases.chave.GetMovChaveOpenList
import br.com.usinasantafe.pcp.presenter.view.chave.model.ControleChaveModel
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

data class ControleChaveEditListState(
    val controleChaveModelList: List<ControleChaveModel> = listOf(),
    val flagDialogCheck: Boolean = false,
    val flagCloseAllMov: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<ControleChaveEditListState> {

    override fun copyWithStatus(status: UiStatusState): ControleChaveEditListState =
        copy(status = status)

}

@HiltViewModel
class ControleChaveEditListViewModel @Inject constructor(
    private val getMovChaveOpenList: GetMovChaveOpenList,
    private val closeAllMovChave: CloseAllMovChave,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ControleChaveEditListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ControleChaveEditListState.() -> ControleChaveEditListState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDialogCheck(flagDialogCheck: Boolean) {
        _uiState.update {
            it.copy(flagDialogCheck = flagDialogCheck)
        }
    }

    fun onCloseAllMov() = viewModelScope.launch {
        runCatching {
            closeAllMovChave().getOrThrow()
        }
            .onSuccess { updateState { copy(flagCloseAllMov = true, status = status.copy(flagDialog = false)) } }
            .onFailureState(::updateState)
    }

    fun recoverMovOpenList() = viewModelScope.launch {
        runCatching {
            getMovChaveOpenList().getOrThrow()
        }
            .onSuccess { updateState { copy(controleChaveModelList = it) } }
            .onFailureState(::updateState)
    }
}
