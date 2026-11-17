package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.detalhe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.CloseMovProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetDetalheProprio
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

data class DetalheProprioState(
    val id: Int = 0,
    val dthr: String = "",
    val tipoMov: String = "",
    val veiculo: String = "",
    val motorista: String = "",
    val passageiro: String = "",
    val destino: String = "",
    val veiculoSec: String = "",
    val notaFiscal: String? = "",
    val observ: String? = "",
    val flagDialogCheck: Boolean = false,
    val flagCloseMov: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<DetalheProprioState> {

    override fun copyWithStatus(status: UiStatusState): DetalheProprioState =
        copy(status = status)

}

@HiltViewModel
class DetalheProprioViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getDetalheProprio: GetDetalheProprio,
    private val closeMovProprio: CloseMovProprio
) : ViewModel() {

    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DetalheProprioState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: DetalheProprioState.() -> DetalheProprioState) {
        _uiState.update(block)
    }

    init { updateState { copy(id = idArgs) } }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDialogCheck(flag: Boolean) = updateState { copy(flagDialogCheck = flag) }

    fun recover() = viewModelScope.launch {
        runCatching {
            getDetalheProprio(uiState.value.id).getOrThrow()
        }
            .onSuccess {
                updateState {
                    copy(
                        dthr = it.dthr,
                        tipoMov = it.tipoMov,
                        veiculo = it.veiculo,
                        motorista = it.motorista,
                        passageiro = it.passageiro,
                        destino = it.destino,
                        veiculoSec = it.veiculoSec,
                        notaFiscal = it.notaFiscal,
                        observ = it.observ
                    )
                }
            }
            .onFailureState(::updateState)
    }

    fun onCloseMov() = viewModelScope.launch {
        runCatching {
            closeMovProprio(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(flagCloseMov = it, flagDialogCheck = false) }}
            .onFailureState(::updateState)
    }

}