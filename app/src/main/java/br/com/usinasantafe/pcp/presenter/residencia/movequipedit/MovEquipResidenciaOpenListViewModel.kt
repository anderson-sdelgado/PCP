package br.com.usinasantafe.pcp.presenter.residencia.movequipedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.residencia.CloseSendAllMovResidencia
import br.com.usinasantafe.pcp.domain.usecases.residencia.RecoverListMovEquipResidenciaOpen
import br.com.usinasantafe.pcp.presenter.residencia.movequip.MovEquipResidenciaModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovEquipResidenciaStartedListViewModel @Inject constructor (
    private val closeSendAllMovResidencia: CloseSendAllMovResidencia,
    private val recoverListMovEquipResidenciaOpen: RecoverListMovEquipResidenciaOpen,
): ViewModel() {

    private val _uiLiveData = MutableLiveData<MovEquipResidenciaStartedListFragmentState>()
    val uiLiveData: LiveData<MovEquipResidenciaStartedListFragmentState> = _uiLiveData

    private fun checkCloseAllMov(check: Boolean) {
        _uiLiveData.value = MovEquipResidenciaStartedListFragmentState.CheckCloseAllMov(check)
    }

    private fun setListMovEquip(movEquipList: List<MovEquipResidenciaModel>) {
        _uiLiveData.value = MovEquipResidenciaStartedListFragmentState.ListMovEquip(movEquipList)
    }

    fun closeAllMov() = viewModelScope.launch {
        checkCloseAllMov(closeSendAllMovResidencia())
    }

    fun recoverListMov() = viewModelScope.launch {
        setListMovEquip(recoverListMovEquipResidenciaOpen())
    }

}

sealed class MovEquipResidenciaStartedListFragmentState {
    data class ListMovEquip(val movEquipResidenciaList: List<MovEquipResidenciaModel>) : MovEquipResidenciaStartedListFragmentState()
    data class CheckCloseAllMov(val check: Boolean) : MovEquipResidenciaStartedListFragmentState()
}

