package br.com.usinasantafe.pcp.presenter.residencia.movequip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.RecoverHeader
import br.com.usinasantafe.pcp.domain.usecases.residencia.RecoverListMovEquipResidenciaInside
import br.com.usinasantafe.pcp.domain.usecases.residencia.StartMovEquipResidencia
import br.com.usinasantafe.pcp.presenter.model.HeaderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovEquipResidenciaListViewModel @Inject constructor (
    private val recoverHeader: RecoverHeader,
    private val startMovEquipResidencia: StartMovEquipResidencia,
    private val recoverListMovEquipResidenciaInside: RecoverListMovEquipResidenciaInside,
): ViewModel() {

    private val _uiLiveData = MutableLiveData<MovEquipResidenciaListFragmentState>()
    val uiLiveData: LiveData<MovEquipResidenciaListFragmentState> = _uiLiveData

    private fun checkSetInitialMov(check: Boolean) {
        _uiLiveData.value = MovEquipResidenciaListFragmentState.CheckInitialMovEquip(check)
    }

    private fun setHeader(header: HeaderModel) {
        _uiLiveData.value = MovEquipResidenciaListFragmentState.RecoverHeader(header)
    }

    private fun setListMovEquip(movEquipList: List<MovEquipResidenciaModel>) {
        _uiLiveData.value = MovEquipResidenciaListFragmentState.ListMovEquip(movEquipList)
    }

    fun checkSetInitialMov() = viewModelScope.launch {
        checkSetInitialMov(startMovEquipResidencia())
    }

    fun recoverDataHeader() = viewModelScope.launch {
        setHeader(recoverHeader())
    }

    fun recoverListMov() = viewModelScope.launch {
        setListMovEquip(recoverListMovEquipResidenciaInside())
    }

}

sealed class MovEquipResidenciaListFragmentState {
    data class RecoverHeader(val header: HeaderModel) : MovEquipResidenciaListFragmentState()
    data class ListMovEquip(val movEquipResidenciaList: List<MovEquipResidenciaModel>) : MovEquipResidenciaListFragmentState()
    data class CheckInitialMovEquip(val check: Boolean) : MovEquipResidenciaListFragmentState()
}