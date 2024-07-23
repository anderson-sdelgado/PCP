package br.com.usinasantafe.pcp.presenter.proprio.destino

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetTipoMov
import br.com.usinasantafe.pcp.domain.usecases.proprio.SetDestinoProprio
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMov
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinoProprioViewModel @Inject constructor(
    private val setDestinoProprio: SetDestinoProprio,
    private val getTipoMov: GetTipoMov,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<DestinoProprioFragmentState>()
    val uiLiveData: LiveData<DestinoProprioFragmentState> = _uiLiveData

    private fun checkSetDestino(check: Boolean) {
        _uiLiveData.value = DestinoProprioFragmentState.CheckSetDestino(check)
    }

    fun setDestino(destino: String, flowApp: FlowApp, pos: Int) = viewModelScope.launch {
        checkSetDestino(setDestinoProprio(destino, flowApp, pos))
    }

    fun checkNextFragment() = viewModelScope.launch {
        when(getTipoMov()){
            TypeMov.INPUT -> _uiLiveData.value = DestinoProprioFragmentState.GoFragmentObserv
            TypeMov.OUTPUT -> _uiLiveData.value = DestinoProprioFragmentState.GoFragmentNotaFiscal
            TypeMov.EMPTY -> {}
        }
    }
}

sealed class DestinoProprioFragmentState {
    data class CheckSetDestino(val check: Boolean) : DestinoProprioFragmentState()
    data object GoFragmentObserv : DestinoProprioFragmentState()
    data object GoFragmentNotaFiscal : DestinoProprioFragmentState()
}