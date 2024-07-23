package br.com.usinasantafe.pcp.presenter.residencia.detalhemov

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.residencia.CloseSendMovResidencia
import br.com.usinasantafe.pcp.domain.usecases.residencia.RecoverDetalheMovEquipResidencia
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalheMovEquipResidenciaViewModel @Inject constructor (
    private val recoverDetalheMovEquipResidencia: RecoverDetalheMovEquipResidencia,
    private val closeSendMovResidencia: CloseSendMovResidencia,
): ViewModel() {

    private val _uiLiveData = MutableLiveData<DetalheMovEquipResidenciaFragmentState>()
    val uiLiveData: LiveData<DetalheMovEquipResidenciaFragmentState> = _uiLiveData

    private fun setDetalhe(detalhe: DetalheMovEquipResidenciaModel) {
        _uiLiveData.value = DetalheMovEquipResidenciaFragmentState.RecoverDetalhe(detalhe)
    }

    private fun checkCloseMov(check: Boolean) {
        _uiLiveData.value = DetalheMovEquipResidenciaFragmentState.CheckMov(check)
    }

    fun recoverDataDetalhe(pos: Int) = viewModelScope.launch {
        setDetalhe(recoverDetalheMovEquipResidencia(pos))
    }

    fun closeMov(pos: Int) = viewModelScope.launch {
        checkCloseMov(closeSendMovResidencia(pos))
    }

}

sealed class DetalheMovEquipResidenciaFragmentState {
    data class RecoverDetalhe(val detalhe: DetalheMovEquipResidenciaModel) : DetalheMovEquipResidenciaFragmentState()
    data class CheckMov(val check: Boolean) : DetalheMovEquipResidenciaFragmentState()
}