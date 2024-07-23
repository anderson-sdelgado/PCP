package br.com.usinasantafe.pcp.presenter.visitterc.movequip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.RecoverHeader
import br.com.usinasantafe.pcp.domain.usecases.visitterc.RecoverListMovEquipVisitTercInside
import br.com.usinasantafe.pcp.domain.usecases.visitterc.StartMovEquipVisitTerc
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovEquipVisitTercListViewModel @Inject constructor (
    private val recoverHeader: RecoverHeader,
    private val startMovEquipVisitTerc: StartMovEquipVisitTerc,
    private val recoverListMovEquipVisitTercInside: RecoverListMovEquipVisitTercInside,
): ViewModel() {

    private val _uiLiveData = MutableLiveData<MovEquipVisitTercListFragmentState>()
    val uiLiveData: LiveData<MovEquipVisitTercListFragmentState> = _uiLiveData

    private fun checkSetInitialMov(check: Boolean) {
        _uiLiveData.value = MovEquipVisitTercListFragmentState.CheckInitialMovEquip(check)
    }

    private fun setHeader(header: HeaderModel) {
        _uiLiveData.value = MovEquipVisitTercListFragmentState.RecoverHeader(header)
    }

    private fun setListMovEquip(movEquipList: List<MovEquipVisitTercModel>) {
        _uiLiveData.value = MovEquipVisitTercListFragmentState.ListMovEquip(movEquipList)
    }

    fun checkSetInitialMov() = viewModelScope.launch {
        checkSetInitialMov(startMovEquipVisitTerc())
    }

    fun recoverDataHeader() = viewModelScope.launch {
        setHeader(recoverHeader())
    }

    fun recoverListMov() = viewModelScope.launch {
        setListMovEquip(recoverListMovEquipVisitTercInside())
    }

}

sealed class MovEquipVisitTercListFragmentState {
    data class RecoverHeader(val header: HeaderModel) : MovEquipVisitTercListFragmentState()
    data class ListMovEquip(val movEquipVisitTercList: List<MovEquipVisitTercModel>) : MovEquipVisitTercListFragmentState()
    data class CheckInitialMovEquip(val check: Boolean) : MovEquipVisitTercListFragmentState()
}