package br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.movlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetMovEquipResidenciaInsideList
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.StartInputMovEquipResidencia
import br.com.usinasantafe.pcp.presenter.model.MovEquipResidenciaModel
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

data class MovEquipResidenciaListState(
    val descrVigia: String = "",
    val descrLocal: String = "",
    val list: List<MovEquipResidenciaModel> = emptyList(),
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<MovEquipResidenciaListState> {

    override fun copyWithStatus(status: UiStatusState): MovEquipResidenciaListState =
        copy(status = status)

}

@HiltViewModel
class MovEquipResidenciaListViewModel @Inject constructor(
    private val getHeader: GetHeader,
    private val getMovEquipResidenciaInsideList: GetMovEquipResidenciaInsideList,
    private val startInputMovEquipResidencia: StartInputMovEquipResidencia,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipResidenciaListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: MovEquipResidenciaListState.() -> MovEquipResidenciaListState) {
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
            startInputMovEquipResidencia().getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }

    fun list() = viewModelScope.launch {
        runCatching {
            getMovEquipResidenciaInsideList().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) }}
            .onFailureState(::updateState)
    }
}