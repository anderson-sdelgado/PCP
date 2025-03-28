package br.com.usinasantafe.pcp.presenter.proprio.observ

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetObservProprio
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetTypeMov
import br.com.usinasantafe.pcp.domain.usecases.proprio.SaveMovEquipProprio
import br.com.usinasantafe.pcp.domain.usecases.proprio.SetObservProprio
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ObservProprioState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val typeMov: TypeMovEquip = TypeMovEquip.INPUT,
    val observ: String? = null,
    val flagGetObserv: Boolean = true,
    val flagAccess: Boolean = false,
    val flagReturn: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class ObservProprioViewModel(
    savedStateHandle: SavedStateHandle,
    private val setObservProprio: SetObservProprio,
    private val getObservProprio: GetObservProprio,
    private val saveMovEquipProprio: SaveMovEquipProprio,
    private val getTypeMov: GetTypeMov
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservProprioState())
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

    fun onObservChanged(observ: String) {
        _uiState.update {
            it.copy(observ = observ)
        }
    }

    fun getObserv() = viewModelScope.launch {
        if(
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.flagGetObserv)){
            val resultGetObserv = getObservProprio(
                id = uiState.value.id
            )
            if (resultGetObserv.isFailure) {
                val error = resultGetObserv.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
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
        val resultSetObserv = setObservProprio(
            observ = uiState.value.observ,
            flowApp = uiState.value.flowApp,
            id = uiState.value.id
        )
        if (resultSetObserv.isFailure) {
            val error = resultSetObserv.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        if(uiState.value.flowApp == FlowApp.ADD){
            val resultSaveMovEquip = saveMovEquipProprio()
            if (resultSaveMovEquip.isFailure) {
                val error = resultSaveMovEquip.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
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

    fun setReturn() = viewModelScope.launch {
        if (uiState.value.flowApp == FlowApp.ADD) {
            val resultGetTypeMov = getTypeMov()
            if (resultGetTypeMov.isFailure) {
                val error = resultGetTypeMov.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            val typeMov = resultGetTypeMov.getOrNull()!!
            _uiState.update {
                it.copy(
                    typeMov = typeMov,
                )
            }
        }
        _uiState.update {
            it.copy(
                flagReturn = true,
            )
        }
    }
}