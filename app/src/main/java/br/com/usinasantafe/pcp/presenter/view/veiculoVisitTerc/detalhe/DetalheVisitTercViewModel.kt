package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.detalhe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.CloseMovVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetDetalheVisitTerc
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
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

data class DetalheVisitTercState(
    val id: Int = 0,
    val dthr: String = "",
    val tipoMov: String = "",
    val veiculo: String = "",
    val placa: String = "",
    val tipoVisitTerc: String = "",
    val motorista: String = "",
    val passageiro: String = "",
    val destino: String? = "",
    val observ: String? = "",
    val flagDialogCheck: Boolean = false,
    val flagCloseMov: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<DetalheVisitTercState> {

    override fun copyWithStatus(status: UiStatusState): DetalheVisitTercState =
        copy(status = status)

}

@HiltViewModel
class DetalheVisitTercViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getDetalheVisitTerc: GetDetalheVisitTerc,
    private val closeMovVisitTerc: CloseMovVisitTerc
) : ViewModel() {

    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DetalheVisitTercState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: DetalheVisitTercState.() -> DetalheVisitTercState) {
        _uiState.update(block)
    }

    init { updateState { copy(id = idArgs) } }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDialogCheck(flag: Boolean) = updateState { copy(flagDialogCheck = flag) }

    fun recover() = viewModelScope.launch {
        runCatching {
            getDetalheVisitTerc(uiState.value.id).getOrThrow()
        }
            .onSuccess {
                updateState {
                    copy(
                        dthr = it.dthr,
                        tipoMov = it.tipoMov,
                        veiculo = it.veiculo,
                        placa = it.placa,
                        tipoVisitTerc = it.tipoVisitTerc,
                        motorista = it.motorista,
                        passageiro = it.passageiro,
                        destino = it.destino,
                        observ = it.observ
                    )
                }
            }
            .onFailureState(::updateState)
    }

    fun onCloseMov() = viewModelScope.launch {
        runCatching {
            closeMovVisitTerc(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(flagCloseMov = it, flagDialogCheck = false) }}
            .onFailureState(::updateState)
    }

}