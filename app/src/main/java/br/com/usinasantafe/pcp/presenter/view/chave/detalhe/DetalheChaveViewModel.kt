package br.com.usinasantafe.pcp.presenter.view.chave.detalhe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chave.CloseMovChave
import br.com.usinasantafe.pcp.domain.usecases.chave.GetDetalheMovChave
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
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

data class DetalheChaveState(
    val id: Int = 0,
    val dthr: String = "",
    val tipoMov: String = "",
    val chave: String = "",
    val colab: String = "",
    val observ: String? = "",
    val flagDialogCheck: Boolean = false,
    val flagCloseMov: Boolean = false,
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<DetalheChaveState> {

    override fun copyWithStatus(status: UiStatusState): DetalheChaveState =
        copy(status = status)

}

@HiltViewModel
class DetalheChaveViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getDetalheMovChave: GetDetalheMovChave,
    private val closeMovChave: CloseMovChave
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DetalheChaveState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: DetalheChaveState.() -> DetalheChaveState) {
        _uiState.update(block)
    }

    init {
        _uiState.update {
            it.copy(
                id = id
            )
        }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun onDialogCheck(flagDialogCheck: Boolean) {
        _uiState.update {
            it.copy(flagDialogCheck = flagDialogCheck)
        }
    }

    fun recoverDetalhe() = viewModelScope.launch {
        runCatching {
            getDetalheMovChave(uiState.value.id).getOrThrow()
        }
            .onSuccess { updateState { copy(dthr = it.dthr, tipoMov = it.tipoMov, chave = it.chave, colab = it.colab, observ = it.observ) }}
            .onFailureState(::updateState)
    }

    fun onCloseMov() = viewModelScope.launch {
        runCatching {
            closeMovChave(uiState.value.id).getOrThrow()
        }
            .onSuccess { updateState { copy(flagCloseMov = true, flagDialogCheck = false) }}
            .onFailureState(::updateState)
    }

}