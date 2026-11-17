package br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.detalhe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.CloseMovResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetDetalheResidencia
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.onFailureState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetalheResidenciaState(
    val id: Int = 0,
    val dthr: String = "",
    val tipoMov: String = "",
    val veiculo: String = "",
    val placa: String = "",
    val motorista: String = "",
    val observ: String? = null,
    val flagDialogCheck: Boolean = false,
    val flagCloseMov: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<DetalheResidenciaState> {

    override fun copyWithStatus(status: UiStatusState): DetalheResidenciaState =
        copy(status = status)

}

@HiltViewModel
class DetalheResidenciaViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getDetalheResidencia: GetDetalheResidencia,
    private val closeMovResidencia: CloseMovResidencia
) : ViewModel() {

    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DetalheResidenciaState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: DetalheResidenciaState.() -> DetalheResidenciaState) {
        _uiState.update(block)
    }

    init { updateState { copy(id = idArgs) } }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDialogCheck(flag: Boolean) = updateState { copy(flagDialogCheck = flag) }

    fun recover() = viewModelScope.launch {
        runCatching {
            getDetalheResidencia(uiState.value.id).getOrThrow()
        }
            .onSuccess {
                updateState {
                    copy(
                        dthr = it.dthr,
                        tipoMov = it.tipoMov,
                        veiculo = it.veiculo,
                        placa = it.placa,
                        motorista = it.motorista,
                        observ = it.observ
                    )
                }
            }
            .onFailureState(::updateState)
    }

    fun onCloseMov() = viewModelScope.launch {
        runCatching {
            closeMovResidencia(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(flagCloseMov = it, flagDialogCheck = false) }}
            .onFailureState(::updateState)
    }

}