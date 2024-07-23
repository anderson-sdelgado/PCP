package br.com.usinasantafe.pcp.presenter.residencia.observ

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.residencia.SetObservResidencia
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMov
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObservResidenciaViewModel @Inject constructor(
    private val setObservResidencia: SetObservResidencia,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<ObservResidenciaFragmentState>()
    val uiLiveData: LiveData<ObservResidenciaFragmentState> = _uiLiveData

    private fun checkSetObserv(check: Boolean) {
        _uiLiveData.value = ObservResidenciaFragmentState.CheckSetObserv(check)
    }

    fun setObserv(observ: String?, pos: Int, typeMov: TypeMov, flowApp: FlowApp) = viewModelScope.launch {
        checkSetObserv(setObservResidencia(observ, typeMov, pos, flowApp))
    }

}

sealed class ObservResidenciaFragmentState {
    data class CheckSetObserv(val check: Boolean) : ObservResidenciaFragmentState()
}