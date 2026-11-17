package br.com.usinasantafe.pcp.presenter.view.chaveEquip.detalhe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.CloseMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.GetDetalheMovChaveEquip
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

data class DetalheChaveEquipState(
    val id: Int = 0,
    val dthr: String = "",
    val tipoMov: String = "",
    val equip: String = "",
    val colab: String = "",
    val observ: String? = "",
    val flagDialogCheck: Boolean = false,
    val flagCloseMov: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<DetalheChaveEquipState> {

    override fun copyWithStatus(status: UiStatusState): DetalheChaveEquipState =
        copy(status = status)

}

@HiltViewModel
class DetalheChaveEquipViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getDetalheMovChaveEquip: GetDetalheMovChaveEquip,
    private val closeMovChaveEquip: CloseMovChaveEquip
) : ViewModel() {

    private val idArgs: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DetalheChaveEquipState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: DetalheChaveEquipState.() -> DetalheChaveEquipState) {
        _uiState.update(block)
    }

    init { updateState { copy(id = idArgs) } }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDialogCheck(flag: Boolean)  = updateState { copy(flagDialogCheck = flag) }

    fun recover() = viewModelScope.launch {
        runCatching {
            getDetalheMovChaveEquip(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(dthr = it.dthr, tipoMov = it.tipoMov, equip = it.equip, colab = it.colab, observ = it.observ) }}
            .onFailureState(::updateState)
    }

    fun onCloseMov() = viewModelScope.launch {
        runCatching {
            closeMovChaveEquip(state.id).getOrThrow()
        }
            .onSuccess { updateState { copy(flagCloseMov = it, flagDialogCheck = false) }}
            .onFailureState(::updateState)
    }
}