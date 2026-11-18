package br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.moveditlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.CloseAllMovResidencia
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetMovEquipResidenciaOpenList
import br.com.usinasantafe.pcp.presenter.model.MovEquipResidenciaModel
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.controleeditlist.ControleChaveEquipEditListState
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

data class MovEquipResidenciaEditListState(
    val list: List<MovEquipResidenciaModel> = listOf(),
    val flagDialogCheck: Boolean = false,
    val flagCloseAllMov: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<MovEquipResidenciaEditListState> {

    override fun copyWithStatus(status: UiStatusState): MovEquipResidenciaEditListState =
        copy(status = status)

}

@HiltViewModel
class MovEquipResidenciaEditListViewModel @Inject constructor(
    private val getMovEquipResidenciaOpenList: GetMovEquipResidenciaOpenList,
    private val closeAllMovResidencia: CloseAllMovResidencia,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipResidenciaEditListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: MovEquipResidenciaEditListState.() -> MovEquipResidenciaEditListState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDialogCheck(flag: Boolean) = updateState { copy(flagDialogCheck = flag) }

    fun onCloseAllMov() = viewModelScope.launch {
        runCatching {
            closeAllMovResidencia().getOrThrow()
        }
            .onSuccess { updateState { copy(flagCloseAllMov = true, status = status.copy(flagDialog = false)) } }
            .onFailureState(::updateState)
    }

    fun recover() = viewModelScope.launch {
        runCatching {
            getMovEquipResidenciaOpenList().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureState(::updateState)
    }
}