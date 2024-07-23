package br.com.usinasantafe.pcp.presenter.proprio.veiculoseg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.proprio.DeleteEquipSeg
import br.com.usinasantafe.pcp.domain.usecases.proprio.RecoverListEquipProprioSeg
import br.com.usinasantafe.pcp.utils.TypeAddEquip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VeiculoSegProprioListViewModel @Inject constructor (
    private val deleteEquipSeg: DeleteEquipSeg,
    private val recoverListEquipProprioSeg: RecoverListEquipProprioSeg,
): ViewModel() {

    private val _uiLiveData = MutableLiveData<VeiculoSegProprioListFragmentState>()
    val uiLiveData: LiveData<VeiculoSegProprioListFragmentState> = _uiLiveData

    private fun checkDeleteEquipProprioSeg(check: Boolean) {
        _uiLiveData.value = VeiculoSegProprioListFragmentState.CheckDeleteEquipProprioSeg(check)
    }

    private fun setListEquipSeg(equipSegList: List<String>) {
        _uiLiveData.value = VeiculoSegProprioListFragmentState.ListEquipSeg(equipSegList)
    }

    fun deleteEquip(posList: Int, typeAddEquip: TypeAddEquip, pos: Int) = viewModelScope.launch {
        checkDeleteEquipProprioSeg(deleteEquipSeg(posList, typeAddEquip, pos))
    }

    fun recoverListEquipSeg(typeAddEquip: TypeAddEquip, pos: Int) = viewModelScope.launch {
        setListEquipSeg(recoverListEquipProprioSeg(typeAddEquip, pos))
    }

}

sealed class VeiculoSegProprioListFragmentState {
    data class CheckDeleteEquipProprioSeg(val check: Boolean) : VeiculoSegProprioListFragmentState()
    data class ListEquipSeg(val equipSegList: List<String>) : VeiculoSegProprioListFragmentState()
}