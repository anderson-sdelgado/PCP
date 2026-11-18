package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.nome

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetNomeVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.SetIdVisitTerc
import br.com.usinasantafe.pcp.presenter.Args.CPF_VISIT_TERC_ARGS
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.nomecolab.NomeColabState
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureState
import br.com.usinasantafe.pcp.utils.onSuccessStateAccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class NomeVisitTercState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val tipo: String = "",
    val cpf: String = "",
    val nome: String = "",
    val empresa: String = "",
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<NomeVisitTercState> {

    override fun copyWithStatus(status: UiStatusState): NomeVisitTercState =
        copy(status = status)

}

@HiltViewModel
class NomeVisitTercViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getNomeVisitTerc: GetNomeVisitTerc,
    private val setIdVisitTerc: SetIdVisitTerc,
) : ViewModel() {

    private val cpfArgs: String = saveStateHandle[CPF_VISIT_TERC_ARGS]!!
    private val flowAppArgs: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupanteArgs: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NomeVisitTercState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: NomeVisitTercState.() -> NomeVisitTercState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        val typeOcupante = TypeOcupante.entries[typeOcupanteArgs]
        updateState { copy(cpf = cpfArgs, flowApp = flowApp, typeOcupante = typeOcupante, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recover() = viewModelScope.launch {
        runCatching {
            getNomeVisitTerc(state.cpf, state.flowApp, state.id).getOrThrow()
        }
            .onSuccess {
                updateState {
                    copy(
                        tipo = it.tipo,
                        nome = it.nome,
                        empresa = it.empresa,
                    )
                }
            }
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            setIdVisitTerc(state.cpf, state.flowApp, state.typeOcupante, state.id).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }
}