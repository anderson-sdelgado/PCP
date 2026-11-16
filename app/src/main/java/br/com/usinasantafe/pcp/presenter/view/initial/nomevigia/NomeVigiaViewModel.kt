package br.com.usinasantafe.pcp.presenter.view.initial.nomevigia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.initial.GetNomeVigia
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.nomecolab.NomeColabChaveEquipState
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

data class NomeVigiaState(
    val nomeVigia: String = "",
    override val status: UiStatusState = UiStatusState()
) : UiStateWithStatus<NomeVigiaState> {

    override fun copyWithStatus(status: UiStatusState): NomeVigiaState =
        copy(status = status)

}

@HiltViewModel
class NomeVigiaViewModel @Inject constructor(
    private val getNomeVigia: GetNomeVigia
) : ViewModel() {

    private val _uiState = MutableStateFlow(NomeVigiaState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: NomeVigiaState.() -> NomeVigiaState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recoverNomeVigia() = viewModelScope.launch {
        runCatching {
            getNomeVigia().getOrThrow()
        }
            .onSuccess { updateState { copy(nomeVigia = it) }}
            .onFailureState(::updateState)
    }

}