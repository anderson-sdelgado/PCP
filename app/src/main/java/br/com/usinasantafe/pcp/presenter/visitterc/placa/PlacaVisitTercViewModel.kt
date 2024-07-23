package br.com.usinasantafe.pcp.presenter.visitterc.placa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.visitterc.SetPlacaVisitTerc
import br.com.usinasantafe.pcp.utils.FlowApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacaVisitTercViewModel @Inject constructor(
    private val setPlacaVisitTerc: SetPlacaVisitTerc,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<PlacaVisitTercFragmentState>()
    val uiLiveData: LiveData<PlacaVisitTercFragmentState> = _uiLiveData

    private fun checkSetPlaca(check: Boolean) {
        _uiLiveData.value = PlacaVisitTercFragmentState.CheckSetPlaca(check)
    }

    fun setPlaca(placa: String, flowApp: FlowApp, pos: Int) = viewModelScope.launch {
        checkSetPlaca(setPlacaVisitTerc(placa, flowApp, pos))
    }

}

sealed class PlacaVisitTercFragmentState {
    data class CheckSetPlaca(val check: Boolean) : PlacaVisitTercFragmentState()
}