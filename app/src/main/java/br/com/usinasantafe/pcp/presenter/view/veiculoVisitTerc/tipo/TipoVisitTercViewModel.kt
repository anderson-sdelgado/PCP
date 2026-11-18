package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.tipo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.SetTipoVisitTerc
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.placa.PlacaResidenciaState
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureState
import br.com.usinasantafe.pcp.utils.onSuccessStateAccess
import br.com.usinasantafe.pcp.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class TipoVisitTercState(
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<TipoVisitTercState> {

    override fun copyWithStatus(status: UiStatusState): TipoVisitTercState =
        copy(status = status)

}

@HiltViewModel
class TipoVisitTercViewModel @Inject constructor(
    private val setTipoVisitTerc: SetTipoVisitTerc,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TipoVisitTercState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: TipoVisitTercState.() -> TipoVisitTercState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun set(typeVisitTerc: TypeVisitTerc) = viewModelScope.launch {
        runCatching {
            setTipoVisitTerc(typeVisitTerc).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }

}