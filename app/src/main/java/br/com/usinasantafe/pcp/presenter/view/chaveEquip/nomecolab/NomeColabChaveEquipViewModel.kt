package br.com.usinasantafe.pcp.presenter.view.chaveEquip.nomecolab

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.SetMatricColabMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.StartRemoveMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.common.GetNomeColab
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.MATRIC_COLAB_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeMovKey
import br.com.usinasantafe.pcp.presenter.view.chave.nomecolab.NomeColabChaveState
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

data class NomeColabChaveEquipState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeMov: TypeMovKey = TypeMovKey.RECEIPT,
    val id: Int = 0,
    val matricColab: String = "",
    val nomeColab: String = "",
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<NomeColabChaveEquipState> {

    override fun copyWithStatus(status: UiStatusState): NomeColabChaveEquipState =
        copy(status = status)

}

@HiltViewModel
class NomeColabChaveEquipViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getNomeColab: GetNomeColab,
    private val setMatricColabMovChaveEquip: SetMatricColabMovChaveEquip,
    private val startRemoveMovChaveEquip: StartRemoveMovChaveEquip
) : ViewModel() {

    private val matricColab: String = saveStateHandle[MATRIC_COLAB_ARGS]!!
    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeMov: Int = saveStateHandle[TYPE_MOV_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NomeColabChaveEquipState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: NomeColabChaveEquipState.() -> NomeColabChaveEquipState) {
        _uiState.update(block)
    }

    init {
        _uiState.update {
            it.copy(
                matricColab = matricColab,
                typeMov = TypeMovKey.entries[typeMov],
                flowApp = FlowApp.entries[flowApp],
                id = id
            )
        }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recoverNomeColab() = viewModelScope.launch {
        runCatching {
            getNomeColab(uiState.value.matricColab).getOrThrow()
        }
            .onSuccess { updateState { copy(nomeColab = it) }}
            .onFailureState(::updateState)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            val check = (uiState.value.typeMov == TypeMovKey.REMOVE) && (uiState.value.flowApp == FlowApp.ADD)
            if(check) startRemoveMovChaveEquip(uiState.value.id).getOrThrow()
            setMatricColabMovChaveEquip(uiState.value.matricColab, uiState.value.flowApp, uiState.value.id).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }
}