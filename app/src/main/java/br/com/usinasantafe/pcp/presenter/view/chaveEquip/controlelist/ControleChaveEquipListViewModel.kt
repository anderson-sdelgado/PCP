package br.com.usinasantafe.pcp.presenter.view.chaveEquip.controlelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.GetMovChaveEquipInsideList
import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.StartReceiptMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.presenter.model.ControleChaveEquipModel
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

data class ControleChaveEquipListState(
    val controleChaveEquipModelList: List<ControleChaveEquipModel> = emptyList(),
    val descrVigia: String = "",
    val descrLocal: String = "",
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<ControleChaveEquipListState> {

    override fun copyWithStatus(status: UiStatusState): ControleChaveEquipListState =
        copy(status = status)

}

@HiltViewModel
class ControleChaveEquipListViewModel @Inject constructor(
    private val getHeader: GetHeader,
    private val getMovChaveEquipInsideList: GetMovChaveEquipInsideList,
    private val startReceiptMovChaveEquip: StartReceiptMovChaveEquip
) : ViewModel() {

    private val _uiState = MutableStateFlow(ControleChaveEquipListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ControleChaveEquipListState.() -> ControleChaveEquipListState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recoverHeader() = viewModelScope.launch {
        runCatching {
            getHeader().getOrThrow()
        }
            .onSuccess { updateState { copy(descrVigia = it.descrVigia, descrLocal = it.descrLocal) }}
            .onFailureState(::updateState)
    }

    fun recoverMovList() = viewModelScope.launch {
        runCatching {
            getMovChaveEquipInsideList().getOrThrow()
        }
            .onSuccess { updateState { copy(controleChaveEquipModelList = it) }}
            .onFailureState(::updateState)
    }

    fun startMov() = viewModelScope.launch {
        runCatching {
            startReceiptMovChaveEquip().getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }
}