package br.com.usinasantafe.pcp.presenter.visitterc.movequipedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.visitterc.CloseSendAllMovVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.RecoverListMovEquipVisitTercOpen
import br.com.usinasantafe.pcp.presenter.visitterc.movequip.MovEquipVisitTercModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovEquipVisitTercEmptyListViewModel @Inject constructor (
    private val closeSendAllMovVisitTerc: CloseSendAllMovVisitTerc,
    private val recoverListMovEquipVisitTercOpen: RecoverListMovEquipVisitTercOpen,
): ViewModel() {

    private val _uiLiveData = MutableLiveData<MovEquipVisitTercStartedListFragmentState>()
    val uiLiveData: LiveData<MovEquipVisitTercStartedListFragmentState> = _uiLiveData

    private fun checkCloseAllMov(check: Boolean) {
        _uiLiveData.value = MovEquipVisitTercStartedListFragmentState.CheckCloseAllMov(check)
    }

    private fun setListMovEquip(movEquipList: List<MovEquipVisitTercModel>) {
        _uiLiveData.value = MovEquipVisitTercStartedListFragmentState.ListMovEquip(movEquipList)
    }

    fun closeAllMov() = viewModelScope.launch {
        checkCloseAllMov(closeSendAllMovVisitTerc())
    }

    fun recoverListMov() = viewModelScope.launch {
        setListMovEquip(recoverListMovEquipVisitTercOpen())
    }

}

sealed class MovEquipVisitTercStartedListFragmentState {
    data class ListMovEquip(val movEquipVisitTercList: List<MovEquipVisitTercModel>) : MovEquipVisitTercStartedListFragmentState()
    data class CheckCloseAllMov(val check: Boolean) : MovEquipVisitTercStartedListFragmentState()
}