package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.moveditlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.CloseAllMovVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetMovEquipVisitTercOpenList
import br.com.usinasantafe.pcp.presenter.model.MovEquipVisitTercModel
import br.com.usinasantafe.pcp.utils.UiStateWithStatus
import br.com.usinasantafe.pcp.utils.UiStatusState
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.onFailureState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovEquipVisitTercEditListState(
    val list: List<MovEquipVisitTercModel> = listOf(),
    val flagDialogCheck: Boolean = false,
    val flagCloseAllMov: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<MovEquipVisitTercEditListState> {

    override fun copyWithStatus(status: UiStatusState): MovEquipVisitTercEditListState =
        copy(status = status)

}


@HiltViewModel
class MovEquipVisitTercEditListViewModel @Inject constructor(
    private val getMovEquipVisitTercOpenList: GetMovEquipVisitTercOpenList,
    private val closeAllMovVisitTerc: CloseAllMovVisitTerc,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipVisitTercEditListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: MovEquipVisitTercEditListState.() -> MovEquipVisitTercEditListState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDialogCheck(flag: Boolean) = updateState { copy(flagDialogCheck = flag) }

    fun onCloseAllMov() = viewModelScope.launch {
        runCatching {
            closeAllMovVisitTerc().getOrThrow()
        }
            .onSuccess { updateState { copy(flagCloseAllMov = true, status = status.copy(flagDialog = false)) } }
            .onFailureState(::updateState)
    }

    fun recover() = viewModelScope.launch {
        runCatching {
            getMovEquipVisitTercOpenList().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureState(::updateState)
    }

}