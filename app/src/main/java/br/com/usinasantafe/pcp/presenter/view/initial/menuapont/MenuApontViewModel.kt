package br.com.usinasantafe.pcp.presenter.view.initial.menuapont

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.common.CloseAllMov
import br.com.usinasantafe.pcp.domain.usecases.common.GetStatusSend
import br.com.usinasantafe.pcp.domain.usecases.initial.GetFlowList
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.onFailureState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MenuApontState(
    val flows: List<Fluxo> = emptyList(),
    val descrVigia: String = "",
    val descrLocal: String = "",
    val flagDialogCheck: Boolean = false,
    val flagReturn: Boolean = false,
    val statusSend: StatusSend = StatusSend.STARTED,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<MenuApontState> {

    override fun copyWithStatus(status: UiStatusState): MenuApontState =
        copy(status = status)

}

@HiltViewModel
class MenuApontViewModel @Inject constructor(
    private val getFlowList: GetFlowList,
    private val getHeader: GetHeader,
    private val closeAllMov: CloseAllMov,
    private val getStatusSend: GetStatusSend,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuApontState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: MenuApontState.() -> MenuApontState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    fun onDialogCheck(flag: Boolean) = updateState { copy(flagDialogCheck = flag) }

    init {
        observeStatusSend()
    }

    private fun observeStatusSend() =
        viewModelScope.launch {
            getStatusSend()
                .catch {
                    updateState {
                        copy(
                            status = status.copy(
                                flagDialog = true,
                                flagFailure = true,
                                failure = it.message ?: ""
                            )
                        )
                    }
                }
                .collect { statusSend ->
                    updateState {
                        copy(statusSend = statusSend)
                    }
                }
        }

    fun recover() = viewModelScope.launch {
        runCatching {
            getHeader().getOrThrow()
        }
            .onSuccess { updateState { copy(descrVigia = it.descrVigia, descrLocal = it.descrLocal) }}
            .onFailureState(::updateState)
    }

    fun flowList() = viewModelScope.launch {
        runCatching {
            getFlowList().getOrThrow()
        }
            .onSuccess { updateState { copy(flows = it) }}
            .onFailureState(::updateState)
    }

    fun onCloseAllMovOpen() = viewModelScope.launch {
        runCatching {
            closeAllMov().getOrThrow()
        }
            .onSuccess { updateState { copy(flagReturn = it, flagDialogCheck = false) }}
            .onFailureState(::updateState)
    }

}