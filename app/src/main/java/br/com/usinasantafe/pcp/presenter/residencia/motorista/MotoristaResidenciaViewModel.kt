package br.com.usinasantafe.pcp.presenter.residencia.motorista

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.residencia.SetMotoristaResidencia
import br.com.usinasantafe.pcp.utils.FlowApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MotoristaResidenciaViewModel @Inject constructor(
    private val setMotoristaResidencia: SetMotoristaResidencia,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<MotoristaResidenciaFragmentState>()
    val uiLiveData: LiveData<MotoristaResidenciaFragmentState> = _uiLiveData

    private fun checkSetMotorista(check: Boolean) {
        _uiLiveData.value = MotoristaResidenciaFragmentState.CheckSetMotorista(check)
    }

    fun setMotorista(veiculo: String, flowApp: FlowApp, pos: Int) = viewModelScope.launch {
        checkSetMotorista(setMotoristaResidencia(veiculo, flowApp, pos))
    }

}

sealed class MotoristaResidenciaFragmentState {
    data class CheckSetMotorista(val check: Boolean) : MotoristaResidenciaFragmentState()
}