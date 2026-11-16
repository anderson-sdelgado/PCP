package br.com.usinasantafe.pcp.presenter.view.configuration.senha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.config.CheckPassword
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
import kotlin.collections.copy

data class SenhaState(
    val password: String = "",
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<SenhaState> {

    override fun copyWithStatus(status: UiStatusState): SenhaState =
        copy(status = status)

}

@HiltViewModel
class SenhaViewModel @Inject constructor(
    private val checkPassword: CheckPassword
) : ViewModel() {

    private val _uiState = MutableStateFlow(SenhaState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: SenhaState.() -> SenhaState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onPasswordChanged(password: String) = updateState { copy(password = password) }

    fun onCheckAccess() = viewModelScope.launch {
        runCatching {
            checkPassword(state.password).getOrThrow()
        }
            .onSuccess { updateState { copy(status = status.copy(flagAccess = it)) } }
            .onFailureState(getClassAndMethod(), ::updateState)
    }

}