package br.com.usinasantafe.pcp.presenter.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.initial.AdjustConfig
import br.com.usinasantafe.pcp.domain.usecases.initial.CheckMovOpen
import br.com.usinasantafe.pcp.domain.usecases.initial.DeleteMovSent
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.onFailureState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashState(
    val flagMovOpen: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<SplashState> {

    override fun copyWithStatus(status: UiStatusState): SplashState =
        copy(status = status)

}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val adjustConfig: AdjustConfig,
    private val deleteMovSent: DeleteMovSent,
    private val checkMovOpen: CheckMovOpen
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: SplashState.() -> SplashState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun initial(version: String) = viewModelScope.launch {
        runCatching {
            adjustConfig(version).getOrThrow()
            deleteMovSent().getOrThrow()
            checkMovOpen().getOrThrow()
        }
            .onSuccess{  updateState { copy(flagMovOpen = it, status = status.copy(flagAccess = true)) } }
            .onFailureState(::updateState)
    }

}