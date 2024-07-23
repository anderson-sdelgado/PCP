package br.com.usinasantafe.pcp.presenter.residencia.placa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.residencia.SetPlacaResidencia
import br.com.usinasantafe.pcp.utils.FlowApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacaResidenciaViewModel @Inject constructor(
    private val setPlacaResidencia: SetPlacaResidencia,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<PlacaResidenciaFragmentState>()
    val uiLiveData: LiveData<PlacaResidenciaFragmentState> = _uiLiveData

    private fun checkSetPlaca(check: Boolean) {
        _uiLiveData.value = PlacaResidenciaFragmentState.CheckSetPlaca(check)
    }

    fun setPlaca(placa: String, flowApp: FlowApp, pos: Int) = viewModelScope.launch {
        checkSetPlaca(setPlacaResidencia(placa, flowApp, pos))
    }

}

sealed class PlacaResidenciaFragmentState {
    data class CheckSetPlaca(val check: Boolean) : PlacaResidenciaFragmentState()
}