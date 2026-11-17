package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.movlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.CloseAllMovProprio
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetMovEquipProprioOpenList
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.StartMovEquipProprio
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.presenter.model.MovEquipProprioModel
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

data class MovEquipProprioListState(
    val descrVigia: String = "",
    val descrLocal: String = "",
    val list: List<MovEquipProprioModel> = emptyList(),
    val flagDialogCheck: Boolean = false,
    val flagCloseAllMov: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<MovEquipProprioListState> {

    override fun copyWithStatus(status: UiStatusState): MovEquipProprioListState =
        copy(status = status)

}

@HiltViewModel
class MovEquipProprioListViewModel @Inject constructor(
    private val getHeader: GetHeader,
    private val startMovEquipProprio: StartMovEquipProprio,
    private val getMovEquipProprioOpenList: GetMovEquipProprioOpenList,
    private val closeAllMovProprio: CloseAllMovProprio,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipProprioListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: MovEquipProprioListState.() -> MovEquipProprioListState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDialogCheck(flag: Boolean) = updateState { copy(flagDialogCheck = flag) }

    fun recover() = viewModelScope.launch {
        runCatching {
            getHeader().getOrThrow()
        }
            .onSuccess { updateState { copy(descrVigia = it.descrVigia, descrLocal = it.descrLocal) }}
            .onFailureState(::updateState)
    }

    fun list() = viewModelScope.launch {
        runCatching {
            getMovEquipProprioOpenList().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) }}
            .onFailureState(::updateState)
    }

    fun onStartMov(typeMov: TypeMovEquip) = viewModelScope.launch {
        runCatching {
            startMovEquipProprio(typeMov = typeMov).getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }


    fun onCloseAllMov() = viewModelScope.launch {
        runCatching {
            closeAllMovProprio().getOrThrow()
        }
            .onSuccess { updateState { copy(flagCloseAllMov = true) }}
            .onFailureState(::updateState)
    }

}