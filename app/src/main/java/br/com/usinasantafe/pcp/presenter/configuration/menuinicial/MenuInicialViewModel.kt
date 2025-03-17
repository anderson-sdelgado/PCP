package br.com.usinasantafe.pcp.presenter.configuration.menuinicial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetStatusSend
import br.com.usinasantafe.pcp.domain.usecases.config.CheckAccessMain
import br.com.usinasantafe.pcp.utils.StatusSend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MenuInicialState(
    val flagDialog: Boolean = false,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val failure: String = "",
    val failureStatus: String = "",
    val statusSend: StatusSend = StatusSend.STARTED
)

class MenuInicialViewModel(
    private val checkAccessMain: CheckAccessMain,
    private val getStatusSend: GetStatusSend,
) : ViewModel() {

    private val tag = javaClass.simpleName

    private val _uiState = MutableStateFlow(MenuInicialState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun recoverStatusSend() {
        viewModelScope.launch {
            getStatusSend().collect { statusSend ->
                if (statusSend.isFailure) {
                    val error = statusSend.exceptionOrNull()!!
                    val failure =
                        "${tag}.recoverStatusSend -> GetStatusSend -> ${error.message} -> ${error.cause.toString()}"
                    _uiState.update {
                        it.copy(
                            failureStatus = failure
                        )
                    }
                    return@collect
                }
                val status = statusSend.getOrNull()!!
                _uiState.update {
                    it.copy(statusSend = status)
                }
            }
        }
    }

    fun checkAccess() =
        viewModelScope.launch {
            val resultCheckAccess = checkAccessMain()
            if(resultCheckAccess.isFailure){
                val error = resultCheckAccess.exceptionOrNull()!!
                val failure =
                    "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagAccess = false,
                        flagFailure = true,
                        failure = failure
                    )
                }
                return@launch
            }
            val statusAccess = resultCheckAccess.getOrNull()!!
            val statusDialog = !statusAccess
            _uiState.update {
                it.copy(
                    flagDialog = statusDialog,
                    flagAccess = statusAccess,
                    flagFailure = false,
                )
            }
        }
}