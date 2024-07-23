package br.com.usinasantafe.pcp.presenter.proprio.passag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.proprio.DeletePassagColab
import br.com.usinasantafe.pcp.domain.usecases.proprio.RecoverListColabPassag
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassagColabListViewModel @Inject constructor (
    private val recoverListColabPassag: RecoverListColabPassag,
    private val deletePassagColab: DeletePassagColab,
): ViewModel() {

    private val _uiLiveData = MutableLiveData<PassagColabListFragmentState>()
    val uiLiveData: LiveData<PassagColabListFragmentState> = _uiLiveData

    private fun checkDeleteColabPassag(check: Boolean) {
        _uiLiveData.value = PassagColabListFragmentState.CheckDeleteColabPassag(check)
    }

    private fun setListPassag(passagList: List<String>) {
        _uiLiveData.value = PassagColabListFragmentState.ListColabPassag(passagList)
    }

    fun deletePassag(posList: Int, typeAddOcupante: TypeAddOcupante, pos: Int) = viewModelScope.launch {
        checkDeleteColabPassag(deletePassagColab(posList, typeAddOcupante, pos))
    }

    fun recoverListPassag(typeAddOcupante: TypeAddOcupante, pos: Int) = viewModelScope.launch {
        setListPassag(recoverListColabPassag(typeAddOcupante, pos))
    }

}

sealed class PassagColabListFragmentState {
    data class CheckDeleteColabPassag(val check: Boolean) : PassagColabListFragmentState()
    data class ListColabPassag(val passagList: List<String>) : PassagColabListFragmentState()
}