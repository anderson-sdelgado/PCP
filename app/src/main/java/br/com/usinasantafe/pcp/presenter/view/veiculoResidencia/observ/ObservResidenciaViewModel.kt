package br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.observ

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetObservResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.SaveMovEquipResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.SetObservResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.StartOutputMovEquipResidencia
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ObservResidenciaState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeMov: TypeMovEquip = TypeMovEquip.INPUT,
    val id: Int = 0,
    val observ: String? = null,
    val flagGetObserv: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class ObservResidenciaViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setObservResidencia: SetObservResidencia,
    private val getObservResidencia: GetObservResidencia,
    private val startOutputMovEquipResidencia: StartOutputMovEquipResidencia,
    private val saveMovEquipResidencia: SaveMovEquipResidencia
) : ViewModel() {

    private val typeMov: Int = savedStateHandle[TYPE_MOV_ARGS]!!
    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservResidenciaState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                typeMov = TypeMovEquip.entries[typeMov],
                id = id
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onObservChanged(observ: String) {
        _uiState.update {
            it.copy(observ = observ)
        }
    }

    fun recoverObserv() = viewModelScope.launch {
        if(
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.flagGetObserv)
        ) {
            val resultGetObserv = getObservResidencia(
                id = uiState.value.id
            )
            if (resultGetObserv.isFailure) {
                val error = resultGetObserv.exceptionOrNull()!!
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
            val observ = resultGetObserv.getOrNull()
            _uiState.update {
                it.copy(
                    observ = observ,
                    flagGetObserv = false,
                )
            }
        }
    }

    fun setObserv() = viewModelScope.launch {
        if (
            (uiState.value.typeMov == TypeMovEquip.OUTPUT) &&
            (uiState.value.flowApp == FlowApp.ADD)
        ) {
            val resultStart = startOutputMovEquipResidencia(
                id = uiState.value.id
            )
            if(resultStart.isFailure) {
                val error = resultStart.exceptionOrNull()!!
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
        }
        val resultSetObserv = setObservResidencia(
            observ = uiState.value.observ,
            flowApp = uiState.value.flowApp,
            id = uiState.value.id,
        )
        if(resultSetObserv.isFailure) {
            val error = resultSetObserv.exceptionOrNull()!!
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
        if(uiState.value.flowApp == FlowApp.ADD){
            val resultSaveMovEquip = saveMovEquipResidencia(
                typeMov = uiState.value.typeMov,
                id = uiState.value.id
            )
            if (resultSaveMovEquip.isFailure) {
                val error = resultSaveMovEquip.exceptionOrNull()!!
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
        }
        _uiState.update {
            it.copy(
                flagAccess = true,
            )
        }
    }

}