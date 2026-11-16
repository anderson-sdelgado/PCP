package br.com.usinasantafe.pcp.presenter.view.configuration.menuinicial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetStatusSend
import br.com.usinasantafe.pcp.domain.usecases.config.CheckAccessMain
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureState
import br.com.usinasantafe.pcp.utils.onSuccessStateAccess
import br.com.usinasantafe.pcp.utils.onSuccessUpdateCheckAccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.copy

data class MenuInicialState(
    val statusSend: StatusSend = StatusSend.STARTED,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<MenuInicialState> {

    override fun copyWithStatus(status: UiStatusState): MenuInicialState =
        copy(status = status)

}

@HiltViewModel
class MenuInicialViewModel @Inject constructor(
    private val checkAccessMain: CheckAccessMain,
    private val getStatusSend: GetStatusSend,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuInicialState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: MenuInicialState.() -> MenuInicialState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

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

    fun onCheckAccess() = viewModelScope.launch {
        runCatching {
            checkAccessMain().getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(getClassAndMethod(), ::updateState)
    }

}