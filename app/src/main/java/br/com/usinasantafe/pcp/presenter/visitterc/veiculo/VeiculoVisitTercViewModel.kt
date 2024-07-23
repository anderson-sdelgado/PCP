package br.com.usinasantafe.pcp.presenter.visitterc.veiculo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.domain.usecases.visitterc.SetVeiculoVisitTerc
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VeiculoVisitTercViewModel @Inject constructor(
    private val setVeiculoVisitTerc: SetVeiculoVisitTerc,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<VeiculoVisitTercFragmentState>()
    val uiLiveData: LiveData<VeiculoVisitTercFragmentState> = _uiLiveData

    private fun checkSetVeiculo(check: Boolean) {
        _uiLiveData.value = VeiculoVisitTercFragmentState.CheckSetVeiculo(check)
    }

    fun setVeiculo(veiculo: String, flowApp: FlowApp, pos: Int) = viewModelScope.launch {
        checkSetVeiculo(setVeiculoVisitTerc(veiculo, flowApp, pos))
    }

}

sealed class VeiculoVisitTercFragmentState {
    data class CheckSetVeiculo(val check: Boolean) : VeiculoVisitTercFragmentState()
}