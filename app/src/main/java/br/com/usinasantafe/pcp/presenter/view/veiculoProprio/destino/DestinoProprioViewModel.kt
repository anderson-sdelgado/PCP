package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.destino

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetDestinoProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetTypeMov
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SetDestinoProprio
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.observ.ObservChaveEquipState
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureState
import br.com.usinasantafe.pcp.utils.onFailureUpdate
import br.com.usinasantafe.pcp.utils.onSuccessUpdateCheckAccess
import br.com.usinasantafe.pcp.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class DestinoProprioState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val text: String = "",
    val typeMov: TypeMovEquip = TypeMovEquip.INPUT,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<DestinoProprioState> {

    override fun copyWithStatus(status: UiStatusState): DestinoProprioState =
        copy(status = status)

}

@HiltViewModel
class DestinoProprioViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setDestinoProprio: SetDestinoProprio,
    private val getDestinoProprio: GetDestinoProprio,
    private val getTypeMov: GetTypeMov
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DestinoProprioState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: DestinoProprioState.() -> DestinoProprioState) {
        _uiState.update(block)
    }

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                id = id
            )
        }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDestinoChanged(text: String) = updateState { copy(text = text) }

    fun recoverDestino() = viewModelScope.launch {
        runCatching {
            if(uiState.value.flowApp == FlowApp.ADD) return@launch
            getDestinoProprio(uiState.value.id).getOrThrow()
        }
            .onSuccess { updateState { copy(text = it) } }
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            if(state.text.isEmpty()) {
                updateState { withFailure( Errors.FIELD_EMPTY) }
                return@launch
            }
            setDestinoProprio(uiState.value.text, uiState.value.flowApp, uiState.value.id).getOrThrow()
        }
            .onSuccessUpdateCheckAccess(::updateState)
            .onFailureUpdate(::updateState)

            val resultSetDestino = setDestinoProprio(
                uiState.value.text,
                uiState.value.flowApp,
                uiState.value.id
            )
            if (resultSetDestino.isFailure) {
                val error = resultSetDestino.exceptionOrNull()!!
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
            if (uiState.value.flowApp == FlowApp.ADD) {
                val resultGetTypeMov = getTypeMov()
                if (resultGetTypeMov.isFailure) {
                    val error = resultGetTypeMov.exceptionOrNull()!!
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
                val typeMov = resultGetTypeMov.getOrNull()!!
                _uiState.update {
                    it.copy(
                        typeMov = typeMov
                    )
                }
            }
            _uiState.update {
                it.copy(
                    flagAccess = true,
                )
            }
    }

}