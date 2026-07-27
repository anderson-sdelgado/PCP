package br.com.usinasantafe.pcp.presenter.view.initial.nomevigia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.initial.GetNomeVigia
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class NomeVigiaState(
    val nomeVigia: String = "",
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class NomeVigiaViewModel @Inject constructor(
    private val getNomeVigia: GetNomeVigia
) : ViewModel() {

    private val _uiState = MutableStateFlow(NomeVigiaState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun returnNomeVigia() = viewModelScope.launch {
        val recoverNome = getNomeVigia()
        if(recoverNome.isFailure){
            val error = recoverNome.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = recoverNome.getOrNull()!!
        _uiState.update {
            it.copy(
                nomeVigia = result
            )
        }
    }

}