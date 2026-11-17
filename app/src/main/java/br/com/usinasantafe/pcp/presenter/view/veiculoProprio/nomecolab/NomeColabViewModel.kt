package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.nomecolab

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetNomeColab
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SetMatricColab
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.MATRIC_COLAB_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.onFailureState
import br.com.usinasantafe.pcp.utils.onSuccessStateAccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NomeColabState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val matricColab: String = "",
    val nomeColab: String = "",
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<NomeColabState> {

    override fun copyWithStatus(status: UiStatusState): NomeColabState =
        copy(status = status)

}

@HiltViewModel
class NomeColabViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getNomeColab: GetNomeColab,
    private val setMatricColab: SetMatricColab
) : ViewModel() {

    private val matricColabArgs: String = saveStateHandle[MATRIC_COLAB_ARGS]!!
    private val flowAppArgs: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupanteArgs: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NomeColabState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: NomeColabState.() -> NomeColabState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        val typeOcupante = TypeOcupante.entries[typeOcupanteArgs]
        updateState { copy(matricColab = matricColabArgs, flowApp = flowApp, typeOcupante = typeOcupante, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recover() = viewModelScope.launch {
        runCatching {
            getNomeColab(state.matricColab).getOrThrow()
        }
            .onSuccess { updateState { copy(nomeColab = it) }}
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            setMatricColab(state.matricColab, state.flowApp, state.typeOcupante, state.id).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }

}