package br.com.usinasantafe.pcp.presenter.visitterc.destino

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.visitterc.SetDestinoVisitTerc
import br.com.usinasantafe.pcp.utils.FlowApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinoVisitTercViewModel @Inject constructor(
    private val setDestinoVisitTerc: SetDestinoVisitTerc,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<DestinoVisitTercFragmentState>()
    val uiLiveData: LiveData<DestinoVisitTercFragmentState> = _uiLiveData

    private fun checkSetDestino(check: Boolean) {
        _uiLiveData.value = DestinoVisitTercFragmentState.CheckSetDestino(check)
    }

    fun setDestino(destino: String, flowApp: FlowApp, pos: Int) = viewModelScope.launch {
        checkSetDestino(setDestinoVisitTerc(destino, flowApp, pos))
    }

}

sealed class DestinoVisitTercFragmentState {
    data class CheckSetDestino(val check: Boolean) : DestinoVisitTercFragmentState()
}