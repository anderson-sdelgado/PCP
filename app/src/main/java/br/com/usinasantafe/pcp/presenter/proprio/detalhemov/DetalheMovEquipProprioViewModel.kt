package br.com.usinasantafe.pcp.presenter.proprio.detalhemov

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.proprio.CloseSendMovProprio
import br.com.usinasantafe.pcp.domain.usecases.proprio.RecoverDetalheMovEquipProprio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalheMovEquipProprioViewModel @Inject constructor (
    private val recoverDetalheMovEquipProprio: RecoverDetalheMovEquipProprio,
    private val closeSendMovProprio: CloseSendMovProprio,
): ViewModel() {

    private val _uiLiveData = MutableLiveData<DetalheMovEquipProprioFragmentState>()
    val uiLiveData: LiveData<DetalheMovEquipProprioFragmentState> = _uiLiveData

    private fun setDetalhe(detalhe: DetalheMovEquipProprioModel) {
        _uiLiveData.value = DetalheMovEquipProprioFragmentState.RecoverDetalhe(detalhe)
    }

    private fun checkCloseMov(check: Boolean) {
        _uiLiveData.value = DetalheMovEquipProprioFragmentState.CheckMov(check)
    }

    fun recoverDataDetalhe(pos: Int) = viewModelScope.launch {
        setDetalhe(recoverDetalheMovEquipProprio(pos))
    }

    fun closeMov(pos: Int) = viewModelScope.launch {
        checkCloseMov(closeSendMovProprio(pos))
    }

}

sealed class DetalheMovEquipProprioFragmentState {
    data class RecoverDetalhe(val detalhe: DetalheMovEquipProprioModel) : DetalheMovEquipProprioFragmentState()
    data class CheckMov(val check: Boolean) : DetalheMovEquipProprioFragmentState()
}