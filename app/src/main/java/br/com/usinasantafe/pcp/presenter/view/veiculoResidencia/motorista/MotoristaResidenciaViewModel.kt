package br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.motorista

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetMotoristaResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.SetMotoristaResidencia
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class MotoristaResidenciaState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val motorista: String = "",
    val checkGetMotorista: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class MotoristaResidenciaViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMotoristaResidencia: GetMotoristaResidencia,
    private val setMotoristaResidencia: SetMotoristaResidencia
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(MotoristaResidenciaState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                id = id
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onMotoristaChanged(motorista: String) {
        _uiState.update {
            it.copy(motorista = motorista)
        }
    }

    fun recoverMotorista() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.checkGetMotorista)
        ) {
            val resultGetMotorista = getMotoristaResidencia(id = uiState.value.id)
            if (resultGetMotorista.isFailure) {
                val error = resultGetMotorista.exceptionOrNull()!!
                val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            val motorista = resultGetMotorista.getOrNull()!!
            _uiState.update {
                it.copy(
                    motorista = motorista,
                    checkGetMotorista = false
                )
            }
        }
    }

    fun setMotorista() {
        if (uiState.value.motorista.isEmpty()) {
            _uiState.update {
                it.copy(
                    flagDialog = true,
                )
            }
            return
        }
        viewModelScope.launch {
            val resultSetMotorista = setMotoristaResidencia(
                motorista = uiState.value.motorista,
                flowApp = uiState.value.flowApp,
                id = uiState.value.id
            )
            if (resultSetMotorista.isFailure) {
                val error = resultSetMotorista.exceptionOrNull()!!
                val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            _uiState.update {
                it.copy(
                    flagAccess = true,
                )
            }
        }
    }

}