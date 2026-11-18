package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.movlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetMovEquipVisitTercInsideList
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.StartInputMovEquipVisitTerc
import br.com.usinasantafe.pcp.presenter.model.MovEquipVisitTercModel
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
import javax.inject.Inject

data class MovEquipVisitTercListState(
    val list: List<MovEquipVisitTercModel> = emptyList(),
    val descrVigia: String = "",
    val descrLocal: String = "",
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<MovEquipVisitTercListState> {

    override fun copyWithStatus(status: UiStatusState): MovEquipVisitTercListState =
        copy(status = status)

}

@HiltViewModel
class MovEquipVisitTercListViewModel @Inject constructor(
    private val getHeader: GetHeader,
    private val getMovEquipVisitTercInsideList: GetMovEquipVisitTercInsideList,
    private val startInputMovEquipVisitTerc: StartInputMovEquipVisitTerc,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipVisitTercListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: MovEquipVisitTercListState.() -> MovEquipVisitTercListState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recover() = viewModelScope.launch {
        runCatching {
            getHeader().getOrThrow()
        }
            .onSuccess { updateState { copy(descrVigia = it.descrVigia, descrLocal = it.descrLocal) }}
            .onFailureState(::updateState)
    }

    fun onStartMov() = viewModelScope.launch {
        runCatching {
            startInputMovEquipVisitTerc().getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }

    fun list() = viewModelScope.launch {
        runCatching {
            getMovEquipVisitTercInsideList().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) }}
            .onFailureState(::updateState)
    }

}