package br.com.usinasantafe.pcp.presenter.view.chave.controlelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chave.GetMovChaveInsideList
import br.com.usinasantafe.pcp.domain.usecases.chave.StartRemoveMovChave
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.presenter.view.chave.model.ControleChaveModel
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

data class ControleChaveListState(
    val controleChaveModelList: List<ControleChaveModel> = emptyList(),
    val descrVigia: String = "",
    val descrLocal: String = "",
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<ControleChaveListState> {

    override fun copyWithStatus(status: UiStatusState): ControleChaveListState =
        copy(status = status)

}

@HiltViewModel
class ControleChaveListViewModel @Inject constructor(
    private val getHeader: GetHeader,
    private val getMovChaveInsideList: GetMovChaveInsideList,
    private val startRemoveMovChave: StartRemoveMovChave
) : ViewModel() {

    private val _uiState = MutableStateFlow(ControleChaveListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ControleChaveListState.() -> ControleChaveListState) {
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
            getMovChaveInsideList().getOrThrow()
        }
            .onSuccess { updateState { copy(controleChaveModelList = it) }}
            .onFailureState(::updateState)
    }

    fun startMov() = viewModelScope.launch {
        runCatching {
            startRemoveMovChave().getOrThrow()
        }
            .onSuccessStateAccess(::updateState)
            .onFailureState(::updateState)
    }
}